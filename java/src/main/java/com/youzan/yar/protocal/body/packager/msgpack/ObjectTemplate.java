package com.youzan.yar.protocal.body.packager.msgpack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;
import org.msgpack.type.ArrayValue;
import org.msgpack.type.FloatValue;
import org.msgpack.type.IntegerValue;
import org.msgpack.type.MapValue;
import org.msgpack.type.RawValue;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;
import org.msgpack.unpacker.Unpacker;

public class ObjectTemplate extends AbstractTemplate<Object> {
	
	static final ObjectTemplate instance = new ObjectTemplate();
	
	private  ObjectTemplate() {
	}
	
	static public ObjectTemplate getInstance() {
		return instance;
	}
	
	
	@Override
	public void write(Packer pk, Object v, boolean required) throws IOException {
		if (v == null) {
			if (required) {
				throw new MessageTypeException("Attempted to write null");
			}
			pk.writeNil();
			return;
		}
		
		pk.write(v);
		
	}

	@Override
	public Object read(Unpacker u, Object to, boolean required)
			throws IOException {
		if (!required && u.trySkipNil()) {
			return null;
		}
		
		return toObject(u.readValue());
	}

	private static Object toObject(Value value) throws IOException  {
		Converter conv = new Converter(value);
		if (value.isNilValue()) {
			return null;
		} else if (value.isRawValue()) {
			RawValue v = value.asRawValue();
			return conv.read(Templates.TString);
		} else if (value.isBooleanValue()) {
			return conv.read(Templates.TBoolean);
		} else if (value.isIntegerValue()) {
			IntegerValue v = value.asIntegerValue();
			return conv.read(Templates.TInteger);
		} else if (value.isFloatValue()) {
			FloatValue v = value.asFloatValue();
			return conv.read(Templates.TFloat);
		} else if (value.isArrayValue()) {
			ArrayValue v = value.asArrayValue();
			List<Object> ret = new ArrayList<Object>(v.size());
			for (Value e : v) {
				ret.add(toObject(e));
			}
			return ret;
		} else if (value.isMapValue()) {
			MapValue v = value.asMapValue();
			
			Map map = new HashMap<>(v.size());
			for (Map.Entry<Value, Value> entry : v.entrySet()) {
				Value key = entry.getKey();
				Value val = entry.getValue();
				
				map.put(toObject(key), toObject(val));
			}
			
			return map;
		} else {
			throw new RuntimeException("fatal error");
		}
 	}
}
