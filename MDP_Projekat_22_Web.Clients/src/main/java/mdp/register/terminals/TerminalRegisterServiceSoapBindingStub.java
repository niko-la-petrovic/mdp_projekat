/**
 * TerminalRegisterServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.register.terminals;

public class TerminalRegisterServiceSoapBindingStub extends org.apache.axis.client.Stub
		implements mdp.register.terminals.TerminalRegisterService {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[8];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("getTerminalsStartingWithName");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "namePrefix"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
				false, false);
		oper.addParameter(param);
		oper.setReturnType(
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto"));
		oper.setReturnClass(mdp.register.terminals.dtos.GetCustomsTerminalDto[].class);
		oper.setReturnQName(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "getTerminalsStartingWithNameReturn"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("searchTerminalSimulation");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "terminalName"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
				false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "passageId"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"),
				java.math.BigInteger.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "isEntry"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false,
				false);
		oper.addParameter(param);
		oper.setReturnType(
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto"));
		oper.setReturnClass(mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
		oper.setReturnQName(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "searchTerminalSimulationReturn"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[1] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("createTerminal");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "dto"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "CreateTerminalDto"),
				mdp.register.terminals.dtos.CreateTerminalDto.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto"));
		oper.setReturnClass(mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://terminals.register.mdp", "createTerminalReturn"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[2] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("getTerminals");
		oper.setReturnType(
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto"));
		oper.setReturnClass(mdp.register.terminals.dtos.GetCustomsTerminalDto[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://terminals.register.mdp", "getTerminalsReturn"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[3] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("searchTerminal");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "dto"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://dtos.mdp", "SearchTerminalDto"), mdp.dtos.SearchTerminalDto.class,
				false, false);
		oper.addParameter(param);
		oper.setReturnType(
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto"));
		oper.setReturnClass(mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://terminals.register.mdp", "searchTerminalReturn"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		oper.addFault(new org.apache.axis.description.FaultDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "fault"),
				"mdp.exceptions.TerminalNotFoundException",
				new javax.xml.namespace.QName("http://exceptions.mdp", "TerminalNotFoundException"), true));
		_operations[4] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("deleteTerminal");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "id"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"),
				java.math.BigInteger.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		oper.addFault(new org.apache.axis.description.FaultDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "fault"),
				"mdp.exceptions.TerminalNotFoundException",
				new javax.xml.namespace.QName("http://exceptions.mdp", "TerminalNotFoundException"), true));
		_operations[5] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("getTerminal");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "passageId"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"),
				java.math.BigInteger.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "isCustomsStep"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false,
				false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "terminalName"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class,
				false, false);
		oper.addParameter(param);
		oper.setReturnType(
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto"));
		oper.setReturnClass(mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://terminals.register.mdp", "getTerminalReturn"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		oper.addFault(new org.apache.axis.description.FaultDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "fault"),
				"mdp.exceptions.TerminalNotFoundException",
				new javax.xml.namespace.QName("http://exceptions.mdp", "TerminalNotFoundException"), true));
		_operations[6] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("updateTerminal");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "dto"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "UpdateTerminalDto"),
				mdp.register.terminals.dtos.UpdateTerminalDto.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(
				new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto"));
		oper.setReturnClass(mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://terminals.register.mdp", "updateTerminalReturn"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[7] = oper;

	}

	public TerminalRegisterServiceSoapBindingStub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public TerminalRegisterServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service)
			throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public TerminalRegisterServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
		java.lang.Class cls;
		javax.xml.namespace.QName qName;
		javax.xml.namespace.QName qName2;
		java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
		java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
		java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
		java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
		java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
		java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
		java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
		java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
		java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
		java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
		qName = new javax.xml.namespace.QName("http://dtos.mdp", "SearchTerminalDto");
		cachedSerQNames.add(qName);
		cls = mdp.dtos.SearchTerminalDto.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "CreateTerminalDto");
		cachedSerQNames.add(qName);
		cls = mdp.register.terminals.dtos.CreateTerminalDto.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageDto");
		cachedSerQNames.add(qName);
		cls = mdp.register.terminals.dtos.GetCustomsPassageDto.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageStepDto");
		cachedSerQNames.add(qName);
		cls = mdp.register.terminals.dtos.GetCustomsPassageStepDto.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsTerminalDto");
		cachedSerQNames.add(qName);
		cls = mdp.register.terminals.dtos.GetCustomsTerminalDto.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "UpdateTerminalDto");
		cachedSerQNames.add(qName);
		cls = mdp.register.terminals.dtos.UpdateTerminalDto.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://exceptions.mdp", "TerminalNotFoundException");
		cachedSerQNames.add(qName);
		cls = mdp.exceptions.TerminalNotFoundException.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://terminals.register.mdp", "ArrayOf_tns1_GetCustomsPassageDto");
		cachedSerQNames.add(qName);
		cls = mdp.register.terminals.dtos.GetCustomsPassageDto[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageDto");
		qName2 = new javax.xml.namespace.QName("http://terminals.register.mdp", "item");
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://terminals.register.mdp", "ArrayOf_tns1_GetCustomsPassageStepDto");
		cachedSerQNames.add(qName);
		cls = mdp.register.terminals.dtos.GetCustomsPassageStepDto[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://dtos.terminals.register.mdp", "GetCustomsPassageStepDto");
		qName2 = new javax.xml.namespace.QName("http://terminals.register.mdp", "item");
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

	}

	protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
		try {
			org.apache.axis.client.Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			java.util.Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				java.lang.String key = (java.lang.String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setEncodingStyle(null);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
						javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames.get(i);
						java.lang.Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							java.lang.Class sf = (java.lang.Class) cachedSerFactories.get(i);
							java.lang.Class df = (java.lang.Class) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories
									.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories
									.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
			}
			return _call;
		} catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public mdp.register.terminals.dtos.GetCustomsTerminalDto[] getTerminalsStartingWithName(java.lang.String namePrefix)
			throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "getTerminalsStartingWithName"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { namePrefix });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto[]) org.apache.axis.utils.JavaUtils
							.convert(_resp, mdp.register.terminals.dtos.GetCustomsTerminalDto[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public mdp.register.terminals.dtos.GetCustomsTerminalDto searchTerminalSimulation(java.lang.String terminalName,
			java.math.BigInteger passageId, boolean isEntry) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(
				new javax.xml.namespace.QName("http://terminals.register.mdp", "searchTerminalSimulation"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call
					.invoke(new java.lang.Object[] { terminalName, passageId, new java.lang.Boolean(isEntry) });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) _resp;
				} catch (java.lang.Exception _exception) {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) org.apache.axis.utils.JavaUtils
							.convert(_resp, mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public mdp.register.terminals.dtos.GetCustomsTerminalDto createTerminal(
			mdp.register.terminals.dtos.CreateTerminalDto dto) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://terminals.register.mdp", "createTerminal"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { dto });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) _resp;
				} catch (java.lang.Exception _exception) {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) org.apache.axis.utils.JavaUtils
							.convert(_resp, mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public mdp.register.terminals.dtos.GetCustomsTerminalDto[] getTerminals() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[3]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://terminals.register.mdp", "getTerminals"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto[]) org.apache.axis.utils.JavaUtils
							.convert(_resp, mdp.register.terminals.dtos.GetCustomsTerminalDto[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public mdp.register.terminals.dtos.GetCustomsTerminalDto searchTerminal(mdp.dtos.SearchTerminalDto dto)
			throws java.rmi.RemoteException, mdp.exceptions.TerminalNotFoundException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[4]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://terminals.register.mdp", "searchTerminal"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { dto });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) _resp;
				} catch (java.lang.Exception _exception) {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) org.apache.axis.utils.JavaUtils
							.convert(_resp, mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			if (axisFaultException.detail != null) {
				if (axisFaultException.detail instanceof java.rmi.RemoteException) {
					throw (java.rmi.RemoteException) axisFaultException.detail;
				}
				if (axisFaultException.detail instanceof mdp.exceptions.TerminalNotFoundException) {
					throw (mdp.exceptions.TerminalNotFoundException) axisFaultException.detail;
				}
			}
			throw axisFaultException;
		}
	}

	public void deleteTerminal(java.math.BigInteger id)
			throws java.rmi.RemoteException, mdp.exceptions.TerminalNotFoundException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[5]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://terminals.register.mdp", "deleteTerminal"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { id });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			extractAttachments(_call);
		} catch (org.apache.axis.AxisFault axisFaultException) {
			if (axisFaultException.detail != null) {
				if (axisFaultException.detail instanceof java.rmi.RemoteException) {
					throw (java.rmi.RemoteException) axisFaultException.detail;
				}
				if (axisFaultException.detail instanceof mdp.exceptions.TerminalNotFoundException) {
					throw (mdp.exceptions.TerminalNotFoundException) axisFaultException.detail;
				}
			}
			throw axisFaultException;
		}
	}

	public mdp.register.terminals.dtos.GetCustomsTerminalDto getTerminal(java.math.BigInteger passageId,
			boolean isCustomsStep, java.lang.String terminalName)
			throws java.rmi.RemoteException, mdp.exceptions.TerminalNotFoundException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[6]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://terminals.register.mdp", "getTerminal"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call
					.invoke(new java.lang.Object[] { passageId, new java.lang.Boolean(isCustomsStep), terminalName });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) _resp;
				} catch (java.lang.Exception _exception) {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) org.apache.axis.utils.JavaUtils
							.convert(_resp, mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			if (axisFaultException.detail != null) {
				if (axisFaultException.detail instanceof java.rmi.RemoteException) {
					throw (java.rmi.RemoteException) axisFaultException.detail;
				}
				if (axisFaultException.detail instanceof mdp.exceptions.TerminalNotFoundException) {
					throw (mdp.exceptions.TerminalNotFoundException) axisFaultException.detail;
				}
			}
			throw axisFaultException;
		}
	}

	public mdp.register.terminals.dtos.GetCustomsTerminalDto updateTerminal(
			mdp.register.terminals.dtos.UpdateTerminalDto dto) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[7]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://terminals.register.mdp", "updateTerminal"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { dto });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) _resp;
				} catch (java.lang.Exception _exception) {
					return (mdp.register.terminals.dtos.GetCustomsTerminalDto) org.apache.axis.utils.JavaUtils
							.convert(_resp, mdp.register.terminals.dtos.GetCustomsTerminalDto.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

}
