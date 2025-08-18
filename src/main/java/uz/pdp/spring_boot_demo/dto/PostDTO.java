package uz.pdp.spring_boot_demo.dto;

import lombok.Getter;

@Getter
public class PostDTO {
    private Integer id;
    private String title;

    public PostDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }
}
