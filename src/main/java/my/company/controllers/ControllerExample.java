package my.company.controllers;

import my.company.entity.Fertilizer;
import my.company.entity.User;
import my.company.service.Fertilizer.FertilizerService;
import my.company.service.Fertilizer.FertilizerServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@Controller
@RequestMapping("/fertilizers")
public class ControllerExample {
    private final FertilizerService fertilizerService;

    public ControllerExample(FertilizerService fertilizerService) {
        this.fertilizerService = fertilizerService;
    }

    @GetMapping
    public String getPage(Model model) {
        model.addAttribute("fertilizers", fertilizerService.getAll(1));
        return "fertilizers";
    }

    @GetMapping("/add")
    public String getPageAdd(Model model, Fertilizer fertilizer) {
        Set<String> set = FertilizerServiceImpl.map.keySet();
        model.addAttribute("names", set);
        model.addAttribute("fertilizer", fertilizer);
        return "fertilizerForm";
    }

    @PostMapping
    public String save(@RequestParam(required = false) Integer id, @RequestParam String name, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        Fertilizer fertilizer = new Fertilizer(id, name, localDate, false);
        fertilizerService.save(fertilizer, 1);
        return "redirect:/fertilizers";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam("id") Integer fertilizerId) {
        Fertilizer fertilizer = fertilizerService.getId(fertilizerId);
        Set<String> set = FertilizerServiceImpl.map.keySet();
        model.addAttribute("names", set);
        model.addAttribute(fertilizer);
        return "fertilizerForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer fertilizerId) {
        fertilizerService.delete(fertilizerId);
        return "redirect:/fertilizers";
    }

    @GetMapping("/over")
    public String over(Model model, @RequestParam("id") Integer fertilizerId) {
        fertilizerService.over(fertilizerId);
        return "redirect:/fertilizers";
    }
}