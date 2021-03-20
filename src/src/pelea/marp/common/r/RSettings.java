package pelea.marp.common.r;

public class RSettings {

	public Boolean exe_model_in_r;//true=we execute the model in r, false=we execute the predictive model
	public String ip;// server ip
	public Integer port;// port to connect
	public String start_server;// command to start the server
	public String model_path;// path of the model
	public String model_type;// type of model
	public String model_file;// file of the model
	public String model_name;
	

	public RSettings(Boolean exe_model_in_r, String ip, Integer port, String c_s, String m_p, String m_t, String model) {
		this.exe_model_in_r=exe_model_in_r;
		this.ip=ip;
		this.port = port;
		this.start_server = c_s;
		this.model_path=System.getProperty("user.dir")+"/"+m_p;
		this.model_type=m_t;
		this.model_file=model;
		if(model.contains("."))
			this.model_name=model.split("\\.")[0];
		else this.model_name=model;
	}


	public RSettings(Boolean exe_model_in_r, String ip, Integer port, String c_s, String m_p, String m_t, String model, String dir) {
		this.exe_model_in_r=exe_model_in_r;
		this.ip=ip;
		this.port = port;
		this.start_server = c_s;
		this.model_path=(dir==null)?System.getProperty("user.dir")+"/"+m_p:dir+"/"+m_p;
		this.model_type=m_t;
		this.model_file=model;
		if(model.contains("."))
			this.model_name=model.split("\\.")[0];
		else this.model_name=model;
	}
}
