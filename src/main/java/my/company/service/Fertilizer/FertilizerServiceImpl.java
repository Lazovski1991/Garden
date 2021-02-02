package my.company.service.Fertilizer;

import my.company.entity.Fertilizer;
import my.company.repository.FertilizerRepository;
import my.company.service.User.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class FertilizerServiceImpl implements FertilizerService {
    public static Map<String, Integer> map = Map.of("синие", 14, "красные", 7, "зеленые", 21);


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

    @Transactional
    @Override
    public void save(Fertilizer fertilizer, Integer userId) {
        if (!fertilizer.isNew()) {
            Fertilizer oldFertilizer = getId(fertilizer.getId());
            oldFertilizer.setLocalDate(fertilizer.getLocalDate());
            oldFertilizer.setName(fertilizer.getName());
            fertilizerRepository.save(oldFertilizer);
        } else {
            fertilizer.setUser(userService.getUserId(userId));
            fertilizerRepository.save(fertilizer);
        }

    }

    @Transactional(readOnly = true)
    @Override
    public Fertilizer getId(Integer fertilizerId) {
        return fertilizerRepository.findById(fertilizerId).orElseThrow();
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
        newFertilizer.setName(oldFertilizer.getName());
        newFertilizer.setUser(oldFertilizer.getUser());
        newFertilizer.setStatus(false);
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            if (pair.getKey().equals(oldFertilizer.getName())) {
                newFertilizer.setLocalDate(oldFertilizer.getLocalDate().plusDays(pair.getValue()));
            }
        }
        fertilizerRepository.deleteById(fertilizerId);
        fertilizerRepository.save(newFertilizer);
    }
}
