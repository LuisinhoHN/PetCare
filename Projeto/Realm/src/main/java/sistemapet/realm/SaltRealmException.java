/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemapet.realm;

/**
 *
 * @author Usuario
 */
public class SaltRealmException extends RuntimeException {

    public SaltRealmException(Exception root) {
        super(root);
    }
}
