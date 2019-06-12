package x.im.server.service; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* MessageService Tester. 
* 
* @author <Authors name> 
* @since <pre>Jun 12, 2019</pre> 
* @version 1.0 
*/ 
public class MessageServiceTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: insertMessage(MessageEntity msg, Channel ch) 
* 
*/ 
@Test
public void testInsertMessage() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: insertGroupMessage(MessageEntity msg, Channel ch) 
* 
*/ 
@Test
public void testInsertGroupMessage() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: findHistory(long user_id, Channel ch) 
* 
*/ 
@Test
public void testFindHistory() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sendMsgWithResponse(MessageEntity msg, Channel ch) 
* 
*/ 
@Test
public void testSendMsgWithResponse() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sendMsg(MessageEntity msg, long to) 
* 
*/ 
@Test
public void testSendMsgForMsgTo() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sendMsg(MessageEntity msg) 
* 
*/ 
@Test
public void testSendMsgMsg() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: messageReceived(List<Long> msgIds) 
* 
*/ 
@Test
public void testMessageReceivedMsgIds() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: messageReceived(long msgId) 
* 
*/ 
@Test
public void testMessageReceivedMsgId() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: responseSuccess(MessageEntity msg, Channel ch) 
* 
*/ 
@Test
public void testResponseSuccess() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = MessageService.getClass().getMethod("responseSuccess", MessageEntity.class, Channel.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: _send(Channel ch, CSMsg csMsg) 
* 
*/ 
@Test
public void test_send() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = MessageService.getClass().getMethod("_send", Channel.class, CSMsg.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
