package org.example.models.profile;

/**
 * Сохранение профиля
 */
public class ProfileDto {

    private String name;
    private String dateOfBirth;
    private String surname;
    private String patronymic;
    private String sex;
    private String phone;

    public ProfileDto() {
    }

    public ProfileDto(String name, String dateOfBirth, String surname, String patronymic, String sex, String phone) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.surname = surname;
        this.patronymic = patronymic;
        this.sex = sex;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
