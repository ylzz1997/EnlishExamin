package bean;
import javax.persistence.*;

/**
 * Created by lhy on 2018/6/11.
 */

@Entity
@Table(name="english_entry")
public class EnlishEntry {
    @Id
    @Column(name = "Id")
    int Id;
    @Column(name = "english")
    String english;
    @Column(name = "chinese")
    String chinese;

    public EnlishEntry() {
    }

    public EnlishEntry(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }
}
