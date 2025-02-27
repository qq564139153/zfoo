/*
 * Copyright (C) 2020 The zfoo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.net.core.tcpSync.client;

import com.zfoo.net.NetContext;
import com.zfoo.net.core.tcp.TcpClient;
import com.zfoo.net.packet.tcp.SyncMessAnswer;
import com.zfoo.net.packet.tcp.SyncMessAsk;
import com.zfoo.protocol.util.JsonUtils;
import com.zfoo.util.ThreadUtils;
import com.zfoo.util.net.HostAndPort;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author godotg
 * @version 3.0
 */
@Ignore
public class TcpClientTest {

    private static final Logger logger = LoggerFactory.getLogger(TcpClientTest.class);

    @Test
    public void startClient() throws Exception {
        var context = new ClassPathXmlApplicationContext("config.xml");

        var client = new TcpClient(HostAndPort.valueOf("127.0.0.1:9000"));
        var session = client.start();

        for (int i = 0; i < 1000; i++) {
            var ask = new SyncMessAsk();
            ask.setMessage("Hello, this is sync client!");
            var answer = NetContext.getRouter().syncAsk(session, ask, SyncMessAnswer.class, null).packet();
            logger.info("同步请求收到结果[{}]", JsonUtils.object2String(answer));
            ThreadUtils.sleep(1000);
        }

        ThreadUtils.sleep(Long.MAX_VALUE);
    }

}
