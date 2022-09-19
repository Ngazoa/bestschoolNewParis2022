/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.prefs.Preferences;
import security.fonctions;

/**
 *
 * @author ASSUS GAMER
 */
public class login {

    Preferences prf = Preferences.userNodeForPackage(fonctions.class);

    private static String login = "localhost";

    public void setLink(String lien) {
        login = lien;
    }

    public String getLink() {
        if (prf.get("lien", null) != null) {
            login = prf.get("lien", null);
        }
        return login;
    }

    public String getLinkImages() {
        if (prf.get("lien", null) != null) {
            login = prf.get("lien", null);
        }
        return "http://" + login+"/images/";
    }
    public String getLinkPhotosEleves() {
        if (prf.get("lien", null) != null) {
            login = prf.get("lien", null);
        }
        return "http://" + login+"/bestSchoolMobile/b/";
    }

}
