package jcanon;


/**
* jcanon/JCanonClientHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/vpc/xprojects/gaming/canon/src/tn/rnu/enit/ateliercorba/jcanon/jcanon.idl
* lundi 15 octobre 2007 19 h 43 CEST
*/

abstract public class JCanonClientHelper
{
  private static String  _id = "IDL:tn.rnu.enit.ateliercorba/jcanon/JCanonClient:1.0";

  public static void insert (org.omg.CORBA.Any a, jcanon.JCanonClient that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static jcanon.JCanonClient extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (jcanon.JCanonClientHelper.id (), "JCanonClient");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static jcanon.JCanonClient read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_JCanonClientStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, jcanon.JCanonClient value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static jcanon.JCanonClient narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof jcanon.JCanonClient)
      return (jcanon.JCanonClient)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      jcanon._JCanonClientStub stub = new jcanon._JCanonClientStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static jcanon.JCanonClient unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof jcanon.JCanonClient)
      return (jcanon.JCanonClient)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      jcanon._JCanonClientStub stub = new jcanon._JCanonClientStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
