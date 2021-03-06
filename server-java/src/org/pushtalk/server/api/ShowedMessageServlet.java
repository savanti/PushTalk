package org.pushtalk.server.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.pushtalk.server.model.User;
import org.pushtalk.server.utils.ServiceUtils;
import org.pushtalk.server.web.common.NormalBaseServlet;

public class ShowedMessageServlet extends NormalBaseServlet {
	private static final long serialVersionUID = 348660245631638687L;
    private static Logger LOG = Logger.getLogger(ShowedMessageServlet.class);
    

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        LOG.debug("api - showed message");
        
        String udid = request.getParameter("udid");
        if (null == udid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "udid is required!");
            return;
        }
        
        User user = talkService.getUserByUdid(udid);
        if (null == user) {
            LOG.warn("the udid is not registered!");
            return;
        }
        
        String friend = request.getParameter("friend");
        String channelName = request.getParameter("channel_name");
        
        if (null == friend && null == channelName) {
            LOG.warn("friend/channel is required!");
            return;
        }
        
        String chatting = null;
        if (null != friend) {
            chatting = ServiceUtils.getChattingFriend(friend);
            
        } else {
            chatting = ServiceUtils.getChattingChannel(channelName);
        }
        
        talkService.showedMessage(udid, chatting);
        LOG.debug("API received chatting - " + chatting);
        
        response.getOutputStream().write("OK".getBytes());
    }

	
}


