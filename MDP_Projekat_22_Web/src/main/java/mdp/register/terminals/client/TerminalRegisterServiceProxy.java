package mdp.register.terminals.client;

public class TerminalRegisterServiceProxy implements mdp.register.terminals.client.TerminalRegisterService {
  private String _endpoint = null;
  private mdp.register.terminals.client.TerminalRegisterService terminalRegisterService = null;
  
  public TerminalRegisterServiceProxy() {
    _initTerminalRegisterServiceProxy();
  }
  
  public TerminalRegisterServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initTerminalRegisterServiceProxy();
  }
  
  private void _initTerminalRegisterServiceProxy() {
    try {
      terminalRegisterService = (new mdp.register.terminals.client.TerminalRegisterServiceServiceLocator()).getTerminalRegisterService();
      if (terminalRegisterService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)terminalRegisterService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)terminalRegisterService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (terminalRegisterService != null)
      ((javax.xml.rpc.Stub)terminalRegisterService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mdp.register.terminals.client.TerminalRegisterService getTerminalRegisterService() {
    if (terminalRegisterService == null)
      _initTerminalRegisterServiceProxy();
    return terminalRegisterService;
  }
  
  public mdp.register.terminals.dtos.GetCustomsTerminalDto[] getTerminalsStartingWithName(java.lang.String namePrefix) throws java.rmi.RemoteException{
    if (terminalRegisterService == null)
      _initTerminalRegisterServiceProxy();
    return terminalRegisterService.getTerminalsStartingWithName(namePrefix);
  }
  
  public mdp.register.terminals.dtos.GetCustomsTerminalDto createTerminal(mdp.register.terminals.dtos.CreateTerminalDto dto) throws java.rmi.RemoteException{
    if (terminalRegisterService == null)
      _initTerminalRegisterServiceProxy();
    return terminalRegisterService.createTerminal(dto);
  }
  
  public mdp.register.terminals.dtos.GetCustomsTerminalDto[] getTerminals() throws java.rmi.RemoteException{
    if (terminalRegisterService == null)
      _initTerminalRegisterServiceProxy();
    return terminalRegisterService.getTerminals();
  }
  
  public void deleteTerminal(java.math.BigInteger id) throws java.rmi.RemoteException, mdp.register.terminals.client.TerminalNotFoundException{
    if (terminalRegisterService == null)
      _initTerminalRegisterServiceProxy();
    terminalRegisterService.deleteTerminal(id);
  }
  
  public mdp.register.terminals.dtos.GetCustomsTerminalDto getTerminal(java.math.BigInteger passageId, boolean isCustomsStep, java.lang.String terminalName) throws java.rmi.RemoteException, mdp.register.terminals.client.TerminalNotFoundException{
    if (terminalRegisterService == null)
      _initTerminalRegisterServiceProxy();
    return terminalRegisterService.getTerminal(passageId, isCustomsStep, terminalName);
  }
  
  public mdp.register.terminals.dtos.GetCustomsTerminalDto searchTerminal(mdp.register.terminals.client.SearchTerminalDto dto) throws java.rmi.RemoteException, mdp.register.terminals.client.TerminalNotFoundException{
    if (terminalRegisterService == null)
      _initTerminalRegisterServiceProxy();
    return terminalRegisterService.searchTerminal(dto);
  }
  
  public mdp.register.terminals.dtos.GetCustomsTerminalDto updateTerminal(mdp.register.terminals.dtos.UpdateTerminalDto dto) throws java.rmi.RemoteException{
    if (terminalRegisterService == null)
      _initTerminalRegisterServiceProxy();
    return terminalRegisterService.updateTerminal(dto);
  }
  
  
}