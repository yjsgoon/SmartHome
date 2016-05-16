package web_project.home;

public class SensorBean {
	private int s_aid;
	private String s_aname;
	private int s_led;
	private int s_usw;
	private int s_hum;
	private int s_flame;
	private int s_vb;
	private int s_lux;
	private int s_noise;
	private int s_temp;
	private int s_gas;
	private int s_pwd;
	
	public SensorBean(){
		
	};
	
	public SensorBean(int s_id, String s_aname, int s_led, int s_usw, int s_hum,
			int s_flame, int s_vibe, int s_lux, int s_noise, int s_temp, int s_gas, int s_pwd) {
		this.s_aid = s_id;
		this.s_aname = s_aname;
		this.s_led = s_led;
		this.s_usw = s_usw;
		this.s_hum = s_hum;
		this.s_flame = s_flame;
		this.s_vb = s_vibe;
		this.s_lux = s_lux;
		this.s_noise = s_noise;
		this.s_temp = s_temp;
		this.s_gas = s_gas;
		this.s_pwd = s_pwd;
	}
	public int getS_vb() {
		return this.s_vb;
	}

	public void setS_vb(int s_vbc) {
		this.s_vb = s_vbc;
	}

	public int getS_temp() {
		return s_temp;
	}

	public void setS_temp(int s_temp) {
		this.s_temp = s_temp;
	}

	public int getS_gas() {
		return s_gas;
	}

	public void setS_gas(int s_gas) {
		this.s_gas = s_gas;
	}

	public int getS_pwd() {
		return s_pwd;
	}

	public void setS_pwd(int s_pwd) {
		this.s_pwd = s_pwd;
	}

	public int getS_aid() {
		return s_aid;
	}
	public void setS_aid(int s_aid) {
		this.s_aid = s_aid;
	}
	public String getS_aname() {
		return s_aname;
	}

	public void setS_aname(String s_aname) {
		this.s_aname = s_aname;
	}
	public int getS_led() {
		return s_led;
	}
	public void setS_led(int s_led) {
		this.s_led = s_led;
	}
	public int getS_usw() {
		return s_usw;
	}
	public void setS_usw(int s_usw) {
		this.s_usw = s_usw;
	}
	public int getS_hum() {
		return s_hum;
	}
	public void setS_hum(int s_hum) {
		this.s_hum = s_hum;
	}
	public int getS_flame() {
		return s_flame;
	}
	public void setS_flame(int s_flame) {
		this.s_flame = s_flame;
	}
	public int getS_noise() {
		return s_noise;
	}
	public void setS_noise(int s_noise) {
		this.s_noise = s_noise;
	}
	public int getS_lux() {
		return s_lux;
	}
	public void setS_lux(int s_lux) {
		this.s_lux = s_lux;
	}
}
