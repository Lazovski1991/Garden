package my.company.service.Fertilizer;

import my.company.entity.Fertilizer;

import java.util.List;

public interface FertilizerService {
    List<Fertilizer> getAll(Integer userId);

    void save(Fertilizer fertilizer, Integer userId);

    Fertilizer getId(Integer fertilizerId);

    void delete(Integer fertilizerId);

    void over(Integer fertilizerId);
}
