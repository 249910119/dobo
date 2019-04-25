/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.sys.security;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dobo.common.utils.JedisUtils;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.sys.utils.UserUtils;

import redis.clients.jedis.Jedis;

/**
 * 通过UPMS统一登录鉴权
 * @author admin
 * @version 2017-8-11
 */
public class WbmUpmsAuthentication {/*
@Service

	private static Logger logger = LoggerFactory.getLogger(WbmUpmsAuthentication.class);

    // 鉴权全局会话key
    public final static String ZHENG_UPMS_SHIRO_SESSION_ID = "zheng-upms-shiro-session-id";
    // 鉴权全局会话key对应用户
    public final static String ZHENG_UPMS_SHIRO_SESSION_ID_USERNAME = "username";
    // 客户端会话key
    public final static String WBM_RESMGR_CLIENT_SESSION_ID = "wbm-resmgr-client-session-id";
    // 客户端访问的URL地址
    public final static String WBM_RESMGR_CLIENT_REQUEST_BACKURL = "wbm-resmgr-client-request-backurl";
    // 客户端会话key存活Session时间
    public final static String WBM_RESMGR_CLIENT_SESSION_ID_UPDATETIME = "wbm-resmgr-client-session-id-updatetime";
    
    /**
     * 更新UPMS统一鉴权登录信息
     * 
     * @param HttpServletRequest request
     * @param Session session
     */
    public static void saveUpmsToSession(HttpServletRequest request, Session session) {
        HttpServletRequest hrq = (HttpServletRequest)request;
		String clientRequestBackurl = hrq.getRequestURL().toString();
		String clientQueryString = hrq.getQueryString();
		if(StringUtils.isNotBlank(clientQueryString)){
			clientRequestBackurl = clientRequestBackurl + "?" + clientQueryString;
		}

		/*String upmsId = request.getParameter(ZHENG_UPMS_SHIRO_SESSION_ID);
		String upmsUsername = request.getParameter(ZHENG_UPMS_SHIRO_SESSION_ID_USERNAME);

		if(upmsId != null && upmsUsername != null && clientRequestBackurl != null && !clientRequestBackurl.contains("/login")) {
			session.setAttribute(ZHENG_UPMS_SHIRO_SESSION_ID, request.getParameter(ZHENG_UPMS_SHIRO_SESSION_ID));
			session.setAttribute(ZHENG_UPMS_SHIRO_SESSION_ID_USERNAME, request.getParameter(ZHENG_UPMS_SHIRO_SESSION_ID_USERNAME));
			session.setAttribute(WBM_RESMGR_CLIENT_REQUEST_BACKURL, clientRequestBackurl);
			
			if(UserUtils.getSubject().isAuthenticated() && !upmsUsername.equals(UserUtils.getUser().getLoginName())) {
				UserUtils.getSubject().logout();
			}

			Long updatetime = (Long) session.getAttribute(WBM_RESMGR_CLIENT_SESSION_ID_UPDATETIME);
			int maxInactiveInterval = request.getSession().getMaxInactiveInterval();
			if(updatetime == null || (System.currentTimeMillis() - updatetime) > maxInactiveInterval * 1000) {
				Jedis jedis = null;
				try {
					jedis = JedisUtils.getResource();
					jedis.setex(WBM_RESMGR_CLIENT_SESSION_ID + "_" + session.getId(), maxInactiveInterval, upmsId);
					session.setAttribute(WBM_RESMGR_CLIENT_SESSION_ID_UPDATETIME, System.currentTimeMillis());
				} catch (Exception e) {
			        logger.warn("saveUpmsToSession->Exception:"+e);
				} finally {
					JedisUtils.returnResource(jedis);
				}
			}
		}*/
    }
    
	/**
	 * 通过UPMS统一登录鉴权自动登录
	 * 
	 * @param request
	 * @return
	 *//*
	public static String autoLogin(HttpServletRequest request){
		//String clientSessionId = (String) UserUtils.getSession().getId();
		String clientSessionId = request.getSession().getId();
		String upmsServerSessionId = (String) request.getSession().getAttribute(ZHENG_UPMS_SHIRO_SESSION_ID);
		String upmsServerSessionIdUsername = (String) request.getSession().getAttribute(ZHENG_UPMS_SHIRO_SESSION_ID_USERNAME);
		String clientRequestBackurl = (String) request.getSession().getAttribute(WBM_RESMGR_CLIENT_REQUEST_BACKURL);
		
		if(StringUtils.isBlank(clientSessionId) || StringUtils.isBlank(upmsServerSessionId) || StringUtils.isBlank(upmsServerSessionIdUsername)){
			return null;
		}
		
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getResource();
			String upmsServerSessionIdValue = jedis.get(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + upmsServerSessionId);
	        
			//通过UPMS统一登录鉴权，判断是否已登录
	        if (StringUtils.isBlank((upmsServerSessionIdValue))) {
	        	return null;
	        }

			//获取服务端对应session内容
			Session upmsShiroSession = SerializableUtil.deserialize(upmsServerSessionIdValue);
			Object upmsUsername = upmsShiroSession.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
	        
			//通过UPMS统一登录鉴权，判断是否已登录
	        if (upmsUsername == null || !upmsServerSessionIdUsername.equals(upmsUsername.toString())) {
	        	return null;
	        }
			
            Subject subject= SecurityUtils.getSubject(); 
            
            if (subject.isAuthenticated()) {    
    			return "redirect:" + clientRequestBackurl;
            } 

	        logger.debug("##########autoLogin->upmsShiroSession:"+upmsShiroSession.getId());
	        logger.debug("##########autoLogin->upmsUsername:"+upmsUsername);

    		String host = StringUtils.getRemoteAddr(request);
    		String newUpmsUserPassward = getUpmsLoginUserPassword(upmsUsername.toString(), host);//自动登录重设校验密码
    		UsernamePasswordToken token = new UsernamePasswordToken(upmsUsername.toString(), newUpmsUserPassward.toCharArray(), false, host, "", false, true);
            subject.login(token); // 自动登录 
            
	        logger.debug("##########autoLogin->subject.isAuthenticated():"+subject.isAuthenticated());

			jedis.setex(WBM_RESMGR_CLIENT_SESSION_ID + "_" + clientSessionId, request.getSession().getMaxInactiveInterval(), upmsServerSessionId);
	        
            if (subject.isAuthenticated()) {    
    			return "redirect:" + clientRequestBackurl;
            } 
		} catch (Exception e) {
	        logger.warn("autoLogin->Exception:"+e);
			return null;
		} finally {
			JedisUtils.returnResource(jedis);
		}
		
		return null;
	}
	
	*//**
<<<<<<< .mine
	 * 设置客户端session与统一登录session关系
	 * 
	 * @param request
	 * @param clientSessionId
	 *//*
	public static void setClientSessionIdCache(ServletRequest request, String clientSessionId){

		//获取从upms传递来的全局session_id，用于用户鉴权使用
		String upmsServerSessionId = request.getParameter(ZHENG_UPMS_SHIRO_SESSION_ID);
		if(StringUtils.isNotBlank(upmsServerSessionId)){
			HttpServletRequest httpRequest =  (HttpServletRequest) request;
			
			//设置缓存最大存活时间（秒）
			int maxInterval = 1800;

			Jedis jedis = null;
			String upmsServerSessionIdValue = null;
			try {
				jedis = JedisUtils.getResource();

				//判断upms对应服务端session是否存在
				upmsServerSessionIdValue = jedis.get(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + upmsServerSessionId);
				if(StringUtils.isNotBlank(upmsServerSessionIdValue)){
					String clientRequestBackurl = httpRequest.getRequestURL().toString();
					String clientQueryString = httpRequest.getQueryString();
					if(StringUtils.isNotBlank(clientQueryString)){
						clientRequestBackurl = clientRequestBackurl + "?" + clientQueryString;
					}
					
					jedis.setex(WBM_RESMGR_CLIENT_SESSION_ID + "_" + clientSessionId, maxInterval, upmsServerSessionId);
					jedis.setex(WBM_RESMGR_CLIENT_REQUEST_BACKURL + "_" + clientSessionId, maxInterval/10, clientRequestBackurl);//过期设置
				}
			} catch (Exception e) {
			} finally {
				JedisUtils.returnResource(jedis);
			}

			//强制客户端退出登录
			if(StringUtils.isBlank(upmsServerSessionIdValue)){
				//UserUtils.getSubject().logout();
			}
		}
	}
	
	*//**
=======
>>>>>>> .r578
	 * 通过UPMS统一登录鉴权，重设鉴权密码
	 * @return
	 *//*
	public static String getUpmsLoginUserPassword(String username, String host){
		return username+host;
	}
	
*/}