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

    Map<String, Integer> map = Map.of("Фалькон", 30, "Солигор", 30,
            "Колосаль", 30, "Борей", 25,
            "Жёлтый", 7, "Миура", 60,
            "Агрон", 60, "Кеонит", 60,
            "Бицепс", 60);

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
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            if (pair.getKey().equalsIgnoreCase(oldFertilizer.getName())) {
                newFertilizer.setLocalDate(now.plusDays(pair.getValue()));
                save(newFertilizer, userId);
            }
        }
        save(oldFertilizer, userId);
    }
}
