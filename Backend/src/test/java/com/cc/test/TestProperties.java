package com.cc.test;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.Pattern;

@ConfigurationProperties(prefix = "cc")
public class TestProperties {

    private boolean debug;

    private final Timeout timeout = new Timeout();
    private final Driver driver = new Driver();
    private final Url url = new Url();
    private final User user = new User();

    //User properties
    public static class User {

        private String username;

        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    //Timeout properties
    public static class Timeout {

        private int shortTimeout;

        private int mediumTimeout;

        private int longTimeout;

        public int getShortTimeout() {
            return shortTimeout;
        }

        public void setShortTimeout(int shortTimeout) {
            this.shortTimeout = shortTimeout;
        }

        public int getMediumTimeout() {
            return mediumTimeout;
        }

        public void setMediumTimeout(int mediumTimeout) {
            this.mediumTimeout = mediumTimeout;
        }

        public int getLongTimeout() {
            return longTimeout;
        }

        public void setLongTimeout(int longTimeout) {
            this.longTimeout = longTimeout;
        }
    }

    //Driver
    public static class Driver {
        @Pattern(regexp = "chrome|firefox|safari")
        private String name;

        private String chromePath;

        private String chromeDriverName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChromePath() {
            return chromePath;
        }

        public void setChromePath(String chromePath) {
            this.chromePath = chromePath;
        }

        public String getChromeDriverName() {
            return chromeDriverName;
        }

        public void setChromeDriverName(String chromeDriverName) {
            this.chromeDriverName = chromeDriverName;
        }
    }

    //Url
    public static class Url {

        private String dev;

        private String prod;

        public String getDev() {
            return dev;
        }

        public void setDev(String dev) {
            this.dev = dev;
        }

        public String getProd() {
            return prod;
        }

        public void setProd(String prod) {
            this.prod = prod;
        }
    }

    public User getUser() {
        return user;
    }

    public Timeout getTimeout() {
        return timeout;
    }

    public Driver getDriver() {
        return driver;
    }

    public Url getUrl() {
        return url;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
