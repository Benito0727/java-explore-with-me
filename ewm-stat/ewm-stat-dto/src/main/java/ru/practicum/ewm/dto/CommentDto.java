package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Long id;                // идентификатор коммента

    private String content;         // содержание коммента

    private Long author;            // идентификатор автора коммента

    private String createdOn;       // дата время создания коммента (в формате yyyy-MM-dd HH:mm:ss)

    private String state;           // состояние - указывает на то что:
                                    // CREATED - коммент создан и не был редактирован
                                    // EDITED - коммент был редактирован автором

    private Boolean isParticipant;  // флаг, является ли автор участиком события

    private String editedOn;        // время и дата изменения коммента (в формате yyyy-MM-dd HH:mm:ss)

    private Long rate;              // рейтинг комментария основаный на оценках пользователей
}
