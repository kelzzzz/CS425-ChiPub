package org.iitcs.gui;

import javax.swing.*;

import java.awt.*;

import static org.iitcs.util.Constants.APP_H;
import static org.iitcs.util.Constants.APP_W;
import static org.iitcs.util.Util.setGridBagConstraints;

public class ApplicationFrame extends JFrame{
    JPanel mainPanel = new JPanel();
    private static final String GENERIC_GREETING = "<html><u>Hi, User!</u></html>";
    public ApplicationFrame(){
        setSize(APP_W,APP_H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void packSimpleFrame(JPanel newPanel){
        setMainPanel(newPanel);
        getContentPane().add(mainPanel);
        refreshPanel();
    }

    public void packFrameWithUserDetailButton(JPanel newPanel, String greetingName){
        setMainPanel(newPanel);

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setGridBagConstraints(c, 0,1, 120);

        getContentPane().add(mainPanel, c);
        addUserDetailPanelToFrame(greetingName);

        refreshPanel();
    }

    private void refreshPanel() {
        revalidate();
        repaint();
    }

    private void setMainPanel(JPanel newPanel) {
        this.mainPanel = newPanel;
        getContentPane().removeAll();
    }

    private void addUserDetailPanelToFrame(String greetingName){
        JPanel userDetailButtonPanel = new JPanel();
        addUserDetailButtonToPanel(greetingName, userDetailButtonPanel);

        GridBagConstraints c = new GridBagConstraints();
        setGridBagConstraints(c, 0, 0, 0);
        c.anchor = GridBagConstraints.LAST_LINE_END;

        getContentPane().add(userDetailButtonPanel, c);
    }

    private static void addUserDetailButtonToPanel(String greetingName, JPanel userMessage) {
        JButton userDetailButton = new JButton(getGreetingWithName(greetingName));
        if(greetingName.isEmpty()){
            userDetailButton.setText(GENERIC_GREETING);
        }
        userDetailButton.setFocusPainted(false);
        userDetailButton.setMargin(new Insets(0, 0, 0, 0));
        userDetailButton.setContentAreaFilled(false);
        userDetailButton.setBorderPainted(false);
        userDetailButton.setOpaque(false);
        userDetailButton.setForeground(Color.BLUE);
        if(ApplicationStateManager.getInstance().userContext == ApplicationStateManager.UserContext.ADMIN){
            userDetailButton.addActionListener(e -> ApplicationStateManager.getInstance()
                    .setState(ApplicationStateManager.GuiState.ADMIN_DASHBOARD));
        }else{
            userDetailButton.addActionListener(e -> ApplicationStateManager.getInstance()
                    .setState(ApplicationStateManager.GuiState.USER_DETAIL));
        }
        userMessage.add(userDetailButton);
    }

    private static String getGreetingWithName(String greetingName) {
        return "<html><u>Hi, ".concat(greetingName).concat("!</u></html>");
    }
}
