package src.main.java;

import com.google.api.client.util.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Reserva {
    private final String selectedService;
    private final DateTime selectedDateTime;
    private final DateTime reservaOneHourLater;

    public Reserva(String selectedService, String dateString, String timeString) throws ParseException {
        this.selectedService = selectedService;
        this.selectedDateTime = parseDateTime(dateString, timeString);
        this.reservaOneHourLater = calculateOneHourLater(selectedDateTime);
    }

    private DateTime parseDateTime(String dateString, String timeString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(dateString);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date time = timeFormat.parse(timeString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));

        return new DateTime(calendar.getTime());
    }

    private DateTime calculateOneHourLater(DateTime dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(dateTime.getValue()));
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return new DateTime(calendar.getTime());
    }

    public String getSelectedService() {
        return selectedService;
    }

    public DateTime getSelectedDateTime() {
        return selectedDateTime;
    }

    public DateTime getReservaOneHourLater() {
        return reservaOneHourLater;
    }
}
