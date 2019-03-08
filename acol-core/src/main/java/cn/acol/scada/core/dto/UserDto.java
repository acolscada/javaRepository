package cn.acol.scada.core.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String customerName;
	private String customerAddress;
	private List<ScadaDto> scadas;
}
