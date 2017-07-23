package org.lizhiwei.lancer;

import org.lizhiwei.lancer.api.Message;
import org.lizhiwei.lancer.internal.LancerMessage;
import org.lizhiwei.lancer.internal.LancerMsgHeader;
import org.lizhiwei.lancer.model.Command;
import org.lizhiwei.lancer.model.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public class ModelHelper {

    public static Message buildMessage() {
        LancerMessage message = new LancerMessage();

        Direction dir1 = new Direction();
        dir1.setDetail("this is direction1");
        dir1.setRaw("balalalalalallla".getBytes());
        dir1.setSteps(new String[]{"raise your refile","aim and wait for fire command",
                "fire your weapon on fire command"});

        Direction dir2 = new Direction();
        dir2.setDetail("this is direction2");
        dir2.setRaw("far boo far booo hmmm".getBytes());
        dir2.setSteps(new String[]{"ride on your horse","accelerate your horse and start to charge",
                "raise your knife and wave"});
        List<Direction> directionList = new ArrayList<>();
        directionList.add(dir1);
        directionList.add(dir2);
        Command cmd = new Command();
        cmd.setDetails(directionList);
        cmd.setFrom("France");
        cmd.setTo("German");
        cmd.setHeavy((short)12);
        cmd.setHeight(89);
        cmd.setLength(8989777322l);

        LancerMsgHeader header = new LancerMsgHeader();
        header.setCharset("UTF-8");
        header.setId(1);
        header.setType(Command.class.getName());

        message.setHeader(header);
        message.setBody(cmd);

        return message;
    }
}
