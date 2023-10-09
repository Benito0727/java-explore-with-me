package ru.practicum.ewm.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchParams {

    private List<Long> usersId;
    private List<String> states;
    private List<Long> categoriesId;
    private String state;
    private String rangeStart;
    private String rangeEnd;
    private String text;
    private Boolean isPaid;
    private Boolean onlyAvailable;
    private String sort;

    /*
    admin events
    @RequestParam(value = "users", required = false) List<Long> usersId,
    @RequestParam(value = "states", required = false) List<String> states,
    @RequestParam(value = "categories", required = false) List<Long> categoriesId,
    @RequestParam(value = "rangeStart", required = false) String rangeStar,
    @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
    @RequestParam(value = "from", defaultValue = "0") Integer from,
    @RequestParam(value = "size", defaultValue = "10") Integer size
    */

    /*
    public events
    @RequestParam(value = "text", required = false) String text,
    @RequestParam(value = "categories", required = false) List<Integer> categoriesId,
    @RequestParam(value = "paid", required = false) Boolean isPaid,
    @RequestParam(value = "rangeStar", required = false) String rangeStar,
    @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
    @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
    @RequestParam(value = "sort", required = false) String sort,
    @RequestParam(value = "from", defaultValue = "0") Integer from,
    @RequestParam(value = "size", defaultValue = "10") Integer size
     */

    /* admin comments
    @RequestParam(value = "users", required = false) List<Long> usersId,
    @RequestParam(value = "rangeStart", required = false) String rangeStart,
    @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
    @RequestParam(value = "from", defaultValue = "0") Integer from,
    @RequestParam(value = "size", defaultValue = "10") Integer size
     */


    // admin comments
    public SearchParams(List<Long> usersId,
                        String state,
                        String rangeStart,
                        String rangeEnd) {
        this.usersId = usersId;
        this.state = state;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }


    // admin events
    public SearchParams(List<Long> usersId,
                        List<String> states,
                        List<Long> categoriesId,
                        String rangeStart,
                        String rangeEnd) {
        this.usersId = usersId;
        this.states = states;
        this.categoriesId = categoriesId;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    // public events
    public SearchParams(String text,
                        List<Long> categoriesId,
                        Boolean isPaid,
                        String rangeStart,
                        String rangeEnd,
                        Boolean onlyAvailable) {
        this.text = text;
        this.categoriesId = categoriesId;
        this.isPaid = isPaid;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.onlyAvailable = onlyAvailable;
    }
}
