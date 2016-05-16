package web_project.controller;

public class SensorVO {
	protected String sensor_id;
	protected String sensor_name;
	protected String sensor_info;
	protected int sensor_state;
	
	//sensor_id의 getter, setter 메서드
	public String getSensor_id(){
		return sensor_id;
	}
	public SensorVO setSensor_id(String sensor_id){
		this.sensor_id = sensor_id;
		return this;
	}
	
	//sensor_name의 getter, setter 메서드
	public String getSensor_name(){
		return sensor_name;
	}
	public SensorVO setSensor_name(String sensor_name){
		this.sensor_name = sensor_name;
		return this;
	}
	
	//sensor_info의 getter, setter 메서드
	public String getSensor_info(){
		return sensor_info;
	}
	public SensorVO setSensor_info(String sensor_info){
		this.sensor_info = sensor_info;
		return this;
	}
	
	//sensor_state의 getter, setter 메서드
	public int getSensor_state(){
		return sensor_state;
	}
	public SensorVO setSensor_state(int sensor_state){
		this.sensor_state = sensor_state;
		return this;
	}
	@Override
	public String toString() {
		return "SensorVO [sensor_id=" + sensor_id + ", sensor_name=" + sensor_name + ", sensor_info=" + sensor_info
				+ ", sensor_state=" + sensor_state + "]\n";
	}
	
	
}
