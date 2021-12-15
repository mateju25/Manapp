package controllers.base;

import controllers.startup.StartController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DateTimeStringConverter;
import lombok.Getter;
import model.Link;
import model.LinkList;
import org.apache.commons.lang3.StringUtils;
import service.LinkService;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MainPageController {
    @FXML
    public TextField textTime;
    @FXML
    public ComboBox<String> comboDay;
    @FXML
    public TextField textLink;
    @FXML
    public ListView<Link> viewLinks;
    @FXML
    public TextField textName;

    private LinkService svc = new LinkService();
    @Getter
    private LinkList linkList = new LinkList();
    private Timer timer = new Timer();
    private Link activeLink = null;

    public void initialize() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        textTime.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00")));
        viewLinks.setCellFactory(param -> new ListCellLink());
        comboDay.getItems().addAll(List.of("Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota", "Nedeľa"));
        if (svc.findAll() == null) {
            StartController.mainStage.close();
            return;
        }
        linkList.setLinks(svc.findAll().getLinks());
        printLinks();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Locale slovakLocale = new Locale("sk");
                String day = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE", slovakLocale));
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm", slovakLocale));
                if (linkList.getLinks() != null) {
                    for (Link dayInLink : linkList.getLinks()) {
                        if (StringUtils.equalsAnyIgnoreCase(dayInLink.getDay(), day) && StringUtils.equalsAnyIgnoreCase(dayInLink.getTime(), time)) {
                            if (StartController.trayIcon != null) {
                                StartController.trayIcon.showMessage(dayInLink.getName());
                                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                    try {
                                        Desktop.getDesktop().browse(new URI(dayInLink.getWeblink()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (URISyntaxException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 0, 50001);
    }

    private void printLinks() {
        viewLinks.getItems().clear();
        if (linkList.getLinks() != null)
            viewLinks.getItems().addAll(svc.findAll().getLinks());
    }

    public void addLink(ActionEvent actionEvent) {
        linkList.setLinks(svc.findAll().getLinks());
        if (activeLink == null) {
            linkList.getLinks().add(new Link(textName.getText(), comboDay.getValue(), textTime.getText(), textLink.getText()));
        } else {
            activeLink.setDay(comboDay.getValue());
            activeLink.setName(textName.getText());
            activeLink.setWeblink(textLink.getText());
            activeLink.setTime(textTime.getText());
        }
        svc.saveLinks(linkList);
        printLinks();
        activeLink = null;
    }

    public void loadItem(MouseEvent mouseEvent) {
        if (viewLinks.getSelectionModel().getSelectedItem() != null) {
            Link item = viewLinks.getSelectionModel().getSelectedItem();
            textName.setText(item.getName());
            textLink.setText(item.getWeblink());
            textTime.setText(item.getTime());
            comboDay.setValue(item.getDay());
            activeLink = item;
        }
    }
}
