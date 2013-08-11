/*
 * Debug class that I use to have a DEBUG boolean in each class
 * I do this so I only have to change it once in this class and the effect
 * will roll through all my other classes
 */

package au.bdr.rFactorServer.util;

/**
 *
 * @author Brendan Russo
 */
public class Debug {
    static final boolean DEBUG = false;
    
    public Debug(){
    }
    
    public boolean getDebug(){
        return DEBUG;
    }
}
