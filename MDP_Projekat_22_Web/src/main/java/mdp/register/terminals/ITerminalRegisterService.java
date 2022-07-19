package mdp.register.terminals;

import java.math.BigInteger;

import mdp.dtos.SearchTerminalDto;
import mdp.exceptions.TerminalNotFoundException;
import mdp.register.terminals.dtos.CreateTerminalDto;
import mdp.register.terminals.dtos.GetTerminalDto;
import mdp.register.terminals.dtos.UpdateTerminalDto;

//public interface ITerminalRegisterService {
//	public GetTerminalDto[] getTerminals();
//
//	public GetTerminalDto createTerminal(CreateTerminalDto dto);
//
//	public GetTerminalDto updateTerminal(UpdateTerminalDto dto) throws TerminalNotFoundException;
//
//	public GetTerminalDto searchTerminal(SearchTerminalDto dto) throws TerminalNotFoundException;
//
//	public void deleteTerminal(BigInteger id) throws TerminalNotFoundException;
//
//	public GetTerminalDto getTerminal(BigInteger passageId, boolean isCustomsStep, String terminalName)
//			throws TerminalNotFoundException;
//}
