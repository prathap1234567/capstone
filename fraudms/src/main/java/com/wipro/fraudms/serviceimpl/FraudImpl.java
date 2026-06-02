package com.wipro.fraudms.serviceimpl;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wipro.fraudms.Config.FeignConfig;
import com.wipro.fraudms.dto.CheckReq;
import com.wipro.fraudms.dto.CheckRes;
import com.wipro.fraudms.dto.TransactionDto;
import com.wipro.fraudms.entity.Alerts;
import com.wipro.fraudms.entity.Blacklist;
import com.wipro.fraudms.entity.TransRules;
import com.wipro.fraudms.repo.AlertsRepo;
import com.wipro.fraudms.repo.BlacklistRepo;
import com.wipro.fraudms.repo.TransRulesRepo;
import com.wipro.fraudms.service.FraudService;

@Service
public class FraudImpl implements FraudService {

    @Autowired
    TransRulesRepo transRulesRepo;
    @Autowired
    AlertsRepo alertsRepo;
    @Autowired
    BlacklistRepo blacklistRepo;
    @Autowired
    FeignConfig feignconfig;

    @Override
    public TransRules saveRule(TransRules rule){

        List<TransRules> rules=transRulesRepo.findAll();

        for(TransRules r:rules){
            r.setActive(false);
            transRulesRepo.save(r);
        }

        rule.setActive(true);
        return transRulesRepo.save(rule);
    }

    @Override
    public TransRules getActiveRule(){
        return transRulesRepo.findByActiveTrue();
    }

    @Override
    public List<TransRules> getAllRules(){
        return transRulesRepo.findAll();
    }

    @Override
    public Blacklist addBlacklist(Blacklist account){
        return blacklistRepo.save(account);
    }

    @Override
    public List<Blacklist> getBlacklist(){
        return blacklistRepo.findAll();
    }

    @Override
    public void deleteBlacklist(int id){
        blacklistRepo.deleteById(id);
    }

    @Override
    public CheckRes checkFraud(CheckReq request){

        TransRules r=transRulesRepo.findByActiveTrue();

        if(r==null){
            throw new RuntimeException(
                    "Fraud rule not configured");
        }

        String reason=null;
        String decision="APPROVED";

        if(request.getAmount()>r.getThreshold()){
            decision="BLOCKED";
            reason="Transaction amount threshold exceeded";
        }

        Blacklist b=blacklistRepo.findByAccountNumber(
                request.getToAccount());

        if(b!=null){
            decision="BLOCKED";
            reason="Beneficiary account is blacklisted";
        }

        if(request.isDeviceMismatch() && r.isDeviceMismatch()){
            decision="SUSPICIOUS";
            reason="Device mismatch detected";
        }

        if(request.getLoginFailureCount()>=r.getLoginFailureChk()){
            decision="SUSPICIOUS";
            reason="High login failure count";
        }

        List<TransactionDto> txs=feignconfig.getTransactionsByAccount(
                request.getFromAccount());

        double todayTotal=0;
        int lastMinuteCount=0;
        double velocityAmount=0;

        LocalDateTime now=LocalDateTime.now();

        for(TransactionDto t:txs){

            if(!request.getFromAccount().equals(t.getFromAccount())){
                continue;
            }

            if(t.getCreatedAt()==null){
                continue;
            }

            if(t.getCreatedAt().toLocalDate().equals(
                    now.toLocalDate())){
                todayTotal=todayTotal+t.getAmount();
            }

            if(t.getCreatedAt().isAfter(now.minusMinutes(1))){
                lastMinuteCount++;
            }

            if(t.getCreatedAt().isAfter(
                    now.minusMinutes(r.getVelocityTime()))){
                velocityAmount=velocityAmount+t.getAmount();
            }
        }

        todayTotal=todayTotal+request.getAmount();
        velocityAmount=velocityAmount+request.getAmount();

        if(todayTotal>r.getDailyLimit()){
            decision="BLOCKED";
            reason="Daily transfer limit exceeded";
        }

        if(lastMinuteCount>=r.getTransfersPerMinute()){
            decision="BLOCKED";
            reason="Too many transfers per minute";
        }

        if(velocityAmount>r.getVelocityLimit()){
            decision="SUSPICIOUS";
            reason="Velocity check failed";
        }

        if(!"APPROVED".equals(decision)){
            saveAlert(request,decision,reason);
        }

        return new CheckRes(decision,reason);
    }

    private void saveAlert(CheckReq request,String decision,String reason){

        Alerts a=new Alerts();

        a.setTransactionId(request.getTransactionId());
        a.setFromAccount(request.getFromAccount());
        a.setToAccount(request.getToAccount());
        a.setAmount(request.getAmount());
        a.setApproval(decision);
        a.setReason(reason);
        a.setCreatedOn(LocalDateTime.now());

        alertsRepo.save(a);
    }

    @Override
    public List<Alerts> getAllAlerts(){
        return alertsRepo.findAll();
    }

    @Override
    public List<Alerts> getAlertsByAccount(String accountNumber){
        return alertsRepo.findByFromAccount(accountNumber);
    }
}