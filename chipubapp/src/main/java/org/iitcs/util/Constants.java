package org.iitcs.util;

import java.util.HashMap;

public class Constants {
    public static final String DEFAULT_PROPERTIES_FILE = "/default_properties.properties";

    public static final String APP_INFO = (
            "<html><span style=font-size:10> This application was made in 2024 for CS425 at the Illinois Institute of Technology. <br/><br/>" +
            "Primary Developer (GUI, JDBC, build), logical SQL @ Kelsey Cavin <br/>" +
            "Reach out to kcavin@hawk.iit.edu for issues with running/building this app. <br/><br/>" +
            "Database schema, CLI, & build scripts @ Andrew Chang-DeWitt <br/><br/>" +
            "Assistance, QA, math @ Peter Capuzzi <br/><br/>" +
            "</span></html>");

    public static final int APP_W = 600;
    public static final int APP_H = 500;
    public static final Long LOGIN_ERROR_CODE = (long) -99999;
}
