package uz.pdp.spring_boot_demo.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "mailing"
)
public class MailingConfigDTO {
    private String server;
    private String user;
    private String password;
    private Boolean enabledtls;


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabledtls() {
        return enabledtls;
    }

    public void setEnabledtls(Boolean enabledtls) {
        this.enabledtls = enabledtls;
    }

    @Override
    public String toString() {
        return "MailingConfigDTO{" +
                "server='" + server + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", enabledtls=" + enabledtls +
                '}';
    }
}
