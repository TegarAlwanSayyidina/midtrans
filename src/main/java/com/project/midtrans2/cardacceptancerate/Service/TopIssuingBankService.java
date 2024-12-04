package com.project.midtrans2.cardacceptancerate.Service;

import com.project.midtrans2.cardacceptancerate.Model.TopIssuingBank;
import com.project.midtrans2.cardacceptancerate.repository.TopIssuingBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopIssuingBankService {

    @Autowired
    private TopIssuingBankRepository repository;

    public List<TopIssuingBank> getAllTopIssuingBank() {
        return repository.findAll();
    }

    public List<TopIssuingBank> getTopIssuingBankBetweenDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.of(1970, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        return repository.findByDateBetween(startDate, endDate);
    }

    public TopIssuingBank saveTopIssuingBank(TopIssuingBank data) {
        try {
            return repository.save(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving TopIssuingBank", e);
        }
    }

    public Map<String, Object> getStatistics(String range) {
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        switch (range.toLowerCase()) {
            case "today":
                startDate = endDate;
                break;
            case "7daysago":
                startDate = endDate.minusDays(7);
                break;
            case "30daysago":
                startDate = endDate.minusDays(30);
                break;
            case "currentmonth":
                startDate = endDate.withDayOfMonth(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid time range. Available options: today, 7daysago, 30daysago, currentmonth.");
        }

        List<TopIssuingBank> topIssuingBanks = repository.findByDateBetween(startDate, endDate);

        Map<String, Object> result = new HashMap<>();
        result.put("transactions", topIssuingBanks.size());
        result.put("data", topIssuingBanks);

        return result;
    }
}