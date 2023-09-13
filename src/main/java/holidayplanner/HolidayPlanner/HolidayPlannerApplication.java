package holidayplanner.HolidayPlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"holidayplanner.HolidayPlanner"})
@SpringBootApplication
public class HolidayPlannerApplication {

	public static void main(String[] args) {
		
		MainClass.main(null);
		SpringApplication.run(HolidayPlannerApplication.class, args);
	}

}
