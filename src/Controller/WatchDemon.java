package Controller;

import Interface.Menu;
import Interface.admin_menu;
import static java.lang.Thread.sleep;
import java.time.LocalDateTime;

/**
 *
 * @author altoma
 */
public class WatchDemon extends Thread{
    public int hours;
    public int minutes;
    public int seconds;
    public Menu menu;
    
    public WatchDemon(Menu menu) {
        this.menu = menu;
    }
    
    public void run(){
        while(true){
            LocalDateTime locaDate = LocalDateTime.now();
            this.hours  = locaDate.getHour();
            this.minutes = locaDate.getMinute();
            this.seconds = locaDate.getSecond();
            try {
                menu.getRelojLabel().setText(hours  + ":"+ minutes +":"+seconds);
                sleep(10);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
