package web_project.controller;

public class SocketVO {
	private static byte state;
	private static byte[] inputDatas = new byte[]{0,0,0};
	
	public static byte getState(){
		return state;
	}
	
	public static void setState(byte state){
		SocketVO.state = state;
	}
	
	public byte[] getInputDatas(){
		return inputDatas;
	}
	
	public void setInputDatas(byte[] inputDatas){
		this.inputDatas = inputDatas;
	}

}
