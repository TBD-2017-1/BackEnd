package ejb;

import PoliTweetsCL.Collector.TwitterStreaming;
import PoliTweetsCL.Core.Resources.Config;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Singleton
public class DaemonEJB {
    @EJB
    private TwitterStreaming streaming;

    private boolean isActive = false;

    Logger logger = Logger.getLogger(getClass().getName());

    //Methods

    public void start(){
        isActive = streaming.start();
    }

    public void stop(){
        isActive = streaming.stop();
    }

    public String toggle(){
        if(isActive) {
            stop();
        }else{
            start();
        }
        return getStatus();
    }

    public String getStatus(){
        return (isActive)?"on":"off";
    }


}