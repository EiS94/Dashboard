package CalendarPackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class EntryBirthday extends CalendarEvent {

    private EntryType type = EntryType.BIRTHDAY;

    private Optional<String> present;
    private int age;
    private String firstName;
    private Optional<String> lastName;


    private String name = "Geburtstag";
    private LocalDate date;


    public EntryBirthday(String firstName, Optional<String> lastName, LocalDate date, int age, Optional<String> present) {
        this.present = present;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
    }

    @Override
    public EntryType getType() {
        return type;
    }

    @Override
    public LocalDate getStartDate() {
        return date;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocalDateTime getLocalDateTimeForSort(){
        LocalDateTime result = date.atTime(0,0);
        return result;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Optional<String> getPresent() {
        return present;
    }

    public void setPresent(Optional<String> present) {
        this.present = present;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    public void setLastName(Optional<String> lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        String lastnameString;
        String presentString;
        if (lastName.isPresent()) lastnameString = lastName.get().toString();
        else lastnameString = "empty";
        if (present.isPresent()) presentString = present.get().toString();
        else presentString = "empty";
        return "Birthday;" + firstName + ";" + lastnameString + ";" + date.toString() + ";"
                + age + ";" + presentString;
    }
}
