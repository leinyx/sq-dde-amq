package cn.hitoplife.DDEAmq.main;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.DDEMLException;
import com.pretty_tools.dde.server.DDEServer;

import cn.hitoplife.DDEAmq.Amq.SqAmqListen;
import cn.hitoplife.DDEAmq.DDE.SqDDEServer;
import cn.hitoplife.DDEAmq.config.SqConfig;

public class SqMain {

	public static void main(String[] args) throws DDEMLException, DDEException {
		// TODO Auto-generated method stub
		SqConfig sqcconfig = new SqConfig();
		SqAmqListen sqAmqListen = new SqAmqListen();
		SqDDEServer sqDDEServer = new SqDDEServer();
		DDEServer server = sqDDEServer.DDE(args,sqcconfig);
		server.start();
		sqAmqListen.listen(server, sqcconfig, args);
		sqcconfig.setNum("50");
		System.out.print(sqcconfig.getNum());
	}

}
