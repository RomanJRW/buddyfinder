package com.joshwindels.buddyfinder.dos;

public class InterestedUser {

    private String username;
    private String emailAddress;
    private String telephoneNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InterestedUser other = (InterestedUser) obj;
        return this.username == other.username
                && this.emailAddress == other.emailAddress
                && this.telephoneNumber == other.telephoneNumber;
    }
}
