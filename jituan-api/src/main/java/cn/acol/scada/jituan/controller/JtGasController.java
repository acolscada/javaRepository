package cn.acol.scada.jituan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.jituan.dto.FailedSca;
import cn.acol.scada.jituan.dto.JtMessageLog;
import cn.acol.scada.jituan.dto.JtRMessage;
import cn.acol.scada.jituan.dto.JtReplayMessage;
import cn.acol.scada.jituan.service.JtServcie;

@RequestMapping("/shgasapi")
@RestController
public class JtGasController {
	@Autowired
	private JtServcie jtServcie;
	@PostMapping("/paigong")
	public JtRMessage paigong(@RequestBody FailedSca failedSca) {
		return jtServcie.paigong(failedSca);
	}
	@PostMapping("/messagelog")
	public JtRMessage messagelog(@RequestBody JtMessageLog jtMessageLog) {
		return jtServcie.messagelog(jtMessageLog);
	}
	@PostMapping("/huitian")
	public SimpleResponse huitian(@RequestBody JtReplayMessage jtReplayMessage) {
		return jtServcie.huitian(jtReplayMessage);
	}
}
