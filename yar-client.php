<?php
	//ini_set("yar.debug", true);



	function getRespData($ch, $resp)
	{
		//echo $resp. PHP_EOL;
		//解析数据
		$arr = unpack("Nheader_id/nheader_version/Nheader_magic_num/Nheader_reserved/a32header_provider/a32header_token/Nheader_body_len/a8package_name/a*data", $resp);

		$data = unserialize($arr['data']);

		print_r($data);

	}

	function post($url, $data)
	{
		$ch = curl_init();
		$opt = array
		(
			CURLOPT_HEADER				=> FALSE,					
			CURLOPT_POST				=> 1,					
			CURLOPT_RETURNTRANSFER		=> true,				


			CURLOPT_URL					=> $url,					
			CURLOPT_POSTFIELDS			=> $data,

			CURLOPT_WRITEFUNCTION		=> "getRespData"

			

		);

		curl_setopt_array($ch, $opt);
	

		$response = curl_exec($ch);

		$errno = curl_errno($ch) or die($errno);
		
		curl_close($ch);


		return $response;
	}

$arr = [['name' => 'lijianwei', 'sex' => '男']];


$id = mt_rand();

echo $id;

$params = [
	'i' => $id,
	'm'	 => 'test',
	'p' => $arr,
];


$str = serialize($params);




$postData = pack("NnNNa32a32N", $id, 0, 0x80DFEC60, 0, "", "", strlen($str)). pack('a8', "PHP"). pack('a*', $str);


post("http://10.211.55.4/server.php", $postData);



