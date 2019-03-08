package cn.acol.scada.core.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponse {
	private int errorCode;
	private String message;
	
	public static SimpleResponse getNormalResponse() {
		return new SimpleResponse(0,"OK");
	}
	public boolean isOk() {
		return errorCode == 0;
	}
	public static SimpleResponse getErrorResponse(String errorMessage) {
		return new SimpleResponse(-1,errorMessage);
	}
}
