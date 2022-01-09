package com.example.HomePS.controller;

import com.example.HomePS.model.PlayStation;
import com.example.HomePS.service.PSService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ps")
@AllArgsConstructor
public class PSController {
    private final PSService psService;

    @PostMapping
    public PlayStation createNewPS(@RequestBody PlayStation playStation){
        return psService.save(playStation);
    }

    @GetMapping
    public Iterable<PlayStation> getPSByPage(
            @RequestParam(required = false, defaultValue = "full") String status,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "psId") String sortBy
    ){
         switch (status) {
            case "free":
                return psService.getPSByStatus(PlayStation.FREE, page - 1, size, sortBy);
            case "busy":
                return psService.getPSByStatus(PlayStation.BUSY, page - 1, size, sortBy);
            case "broken":
                return psService.getPSByStatus(PlayStation.BROKEN, page - 1, size, sortBy);
            default:
                return psService.getPSByPage(page - 1, size, sortBy);
        }
    }

    @GetMapping("/{id}")
    public PlayStation getPS(@PathVariable Long id){
        return psService.getPS(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        psService.delete(id);
    }

    @GetMapping("/search/{query}")
    public List<PlayStation> searchPSByName(@PathVariable String query) {
        return psService.searchPSByName(query);
    }
}