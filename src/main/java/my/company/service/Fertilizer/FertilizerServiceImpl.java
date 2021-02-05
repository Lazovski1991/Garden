package my.company.service.Fertilizer;

import my.company.entity.Fertilizer;
import my.company.repository.FertilizerRepository;
import my.company.service.MailSend;
import my.company.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FertilizerServiceImpl implements FertilizerService {

    public static Map<String, Integer> map() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Фалькон", 30);
        map.put("Солигор", 30);
        map.put("Колосаль", 30);
        map.put("Борей", 25);
        map.put("Жёлтый", 7);
        map.put("Миура", 60);
        map.put("Агрон", 60);
        map.put("Кеонит", 60);
        map.put("Бицепс", 60);
        return map;
    }

    @Autowired
    private MailSend mailSend;

    private final FertilizerRepository fertilizerRepository;
    private final UserService userService;

    public FertilizerServiceImpl(FertilizerRepository fertilizerRepository, UserService userService) {
        this.fertilizerRepository = fertilizerRepository;
        this.userService = userService;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Fertilizer> getAll(Integer userId) {
        return fertilizerRepository.findAll(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Fertilizer> findStatusFalse(Integer userId) {
        return fertilizerRepository.findStatusFalse(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Fertilizer> findStatusTrue(Integer userId) {
        return fertilizerRepository.findStatusTrue(userId);
    }

    @Transactional
    @Override
    public void removeTrueAll(Integer userId) {
        fertilizerRepository.removeTrueAll(userId);
    }

    @Transactional
    @Override
    public void save(Fertilizer fertilizer, Integer userId) {
        if (!fertilizer.isNew()) {
            Fertilizer oldFertilizer = getId(fertilizer.getId());
            oldFertilizer.setLocalDate(fertilizer.getLocalDate());
            oldFertilizer.setName(fertilizer.getName());
            oldFertilizer.setStatus(fertilizer.isStatus());
            fertilizerRepository.save(oldFertilizer);
        } else {
            fertilizer.setUser(userService.getUserId(userId));
            fertilizerRepository.save(fertilizer);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Fertilizer getId(Integer fertilizerId) {
        return fertilizerRepository.findById(fertilizerId).get();
    }

    @Transactional
    @Override
    public void delete(Integer fertilizerId) {
        fertilizerRepository.deleteById(fertilizerId);
        mailSend.sendMailMessage();
    }

    @Transactional
    @Override
    public void over(Integer fertilizerId) {
        Fertilizer oldFertilizer = getId(fertilizerId);
        Fertilizer newFertilizer = new Fertilizer();
        LocalDate now = LocalDate.now();
        newFertilizer.setName(oldFertilizer.getName());
        newFertilizer.setUser(oldFertilizer.getUser());
        newFertilizer.setStatus(false);
        oldFertilizer.setStatus(true);
        oldFertilizer.setLocalDate(now);
        Integer userId = oldFertilizer.getUser().getId();
        for (Map.Entry<String, Integer> pair : map().entrySet()) {
            if (pair.getKey().equalsIgnoreCase(oldFertilizer.getName())) {
                newFertilizer.setLocalDate(now.plusDays(pair.getValue()));
                save(newFertilizer, userId);
            }
        }
        save(oldFertilizer, userId);
    }
}
