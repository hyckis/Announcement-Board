// Author: YICHIN HO

import java.io.Serializable;
import java.util.Date;

public class PostSerializable implements Serializable {
	private String content = "";
	private boolean isLike = false;
	private Date editTime = new Date();
	
	public PostSerializable() {}
	
	public PostSerializable(String content, boolean isLike, Date editTime){
		this.content = content;
		this.isLike = isLike;
		this.editTime = editTime;
	}
	
	public void setContent(String newContent){
		this.content = newContent;
	}
	public void setIsLike(boolean isLike){
		this.isLike = isLike;
	}
	public void setEditTime(Date newEditTime){
		this.editTime = newEditTime;
	}
	
	public String getContent(){
		return this.content;
	}
	public boolean getIsLike(){
		return this.isLike;
	}
	public Date getEditTime(){
		return this.editTime;
	}	
	

}
