package org.example.models.todos;

public class TodoDto {

    private String title;
    private String description;
    private String date;
    private String time;
    private boolean checked;

    public TodoDto() {
    }

    public TodoDto(String title, String description, String date, String time, boolean checked) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
