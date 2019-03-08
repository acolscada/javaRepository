package cn.acol.scada.records.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.scada.records.utils.Utils;

@RestController
@RequestMapping("/de")
public class DeController {
	@PostMapping
	public String de(String src, String key) {
		src = src.replace(" ", "");
		src = src.replace("-", "");
		 byte[] decrypt;
		try {
			decrypt = AES.Decrypt(src, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "无法解密:"+ e.getMessage();
		}
		 return Utils.bytesToHexStringForWatch(decrypt,decrypt.length);
		
	}
	
	@PostMapping("/gzip")
	public String gzip(String src) {
		/*String bytesString = "D6998FF974DDAC5B5AC4EAD10B5ECFC10B659FBD128A1E09082B04"
				+ "67925BB6A9CDBE631C842BE2377CAE2E543531FBA534B97247E8D20F111B01E6F414546A12"
				+ "67F230C4D72C0B68AEB278416EE5E4A555B142376D1D214F9284EA3FC1184F39";*/
		src = src.replace(" ", "");
		String replaceAll = src.replace("-", "");
		
		//byte[] decrypt = AES.Decrypt(bytesString, "0142170608995348414E474841495251");
		byte[] decrypt = GZIPUtils.uncompress(AES.hexStringToBytes(replaceAll));
		
		return Utils.bytesToHexStringForWatch(decrypt, decrypt.length);
	}
	@PostMapping("/de/gzip")
	public String deGzip(String src,String key) {
		src = src.replace("-", "");
		String replaceAll = src.replace(" ", "");
		
		byte[] decrypt;
		try {
			decrypt = AES.Decrypt(replaceAll, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "无法解密：" + e.getMessage();
		}
		decrypt = GZIPUtils.uncompress(decrypt);
		return Utils.bytesToHexStringForWatch(decrypt, decrypt.length);
	}
}
