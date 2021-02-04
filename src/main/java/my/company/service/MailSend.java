package my.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
@PropertySource("classpath:mail.ru.properties")
public class MailSend {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Value("${recipientMail}")
    private String recipientMail;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
    @Value("${dateStartMail}")
    private String dateStart;

    private Date parsingDate;


    public void sendMailMessage() {
        SimpleMailMessage simpleMailMessage1 = new SimpleMailMessage(simpleMailMessage);
        simpleMailMessage1.setTo(recipientMail);
        simpleMailMessage1.setText("Посмотри http://www.tvserials.site/publ/kubik_v_kube/kremnievaja_dolina/3-1-0-1177");

        try {
            parsingDate = simpleDateFormat.parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                javaMailSender.send(simpleMailMessage1);
            }
        };
        timer.scheduleAtFixedRate(task, parsingDate, 1000 * 60);
    }
}
