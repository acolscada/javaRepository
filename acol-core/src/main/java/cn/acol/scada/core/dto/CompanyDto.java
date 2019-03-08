package cn.acol.scada.core.dto;

import lombok.Data;

@Data
public class CompanyDto {
	private Long id;
	private String companyName;
	private String phone;
	private String address;
}
