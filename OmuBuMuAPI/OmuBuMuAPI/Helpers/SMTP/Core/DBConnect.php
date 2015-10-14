<?php

include("Config.php");

class DB {


	static $pdo = null;

	static $charset = 'UTF8';

	static $last_stmt = null;

	public static function instance()
	{
		return 
			self::$pdo == null ?
				self::init() :
				self::$pdo;
	}

	public static function init()
	{
		self::$pdo = new PDO(
			'mysql:host=' . SERVER .';dbname=' . DBNAME,
			USER,
			PASS
		);
		self::$pdo->exec("SET NAMES 'UTF8'");
        self::$pdo->exec("SET character_set_connection = 'UTF8'");
        self::$pdo->exec("SET character_set_client = 'UTF8'");
        self::$pdo->exec("SET character_set_results = 'UTF8'");
		self::$pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_OBJ);

		return self::$pdo;
	}
	
	public static function query($query, $bindings = null)
	{
		if(is_null($bindings))
		{
			if(!self::$last_stmt = self::instance()->query($query))
				return false;
		}
		else
		{
			self::$last_stmt = self::prepare($query);
			if(!self::$last_stmt->execute($bindings))
				return false;
		}

		return self::$last_stmt;
	}

	public static function getVar($query, $bindings = null)
	{
		if(!$stmt = self::query($query, $bindings))
			return false;

		return $stmt->fetchColumn();
	}

	public static function getRow($query, $bindings = null)
	{
		if(!$stmt = self::query($query, $bindings))
			return false;

		return $stmt->fetch();
	}

	public static function get($query, $bindings = null)
	{
		if(!$stmt = self::query($query, $bindings))
			return false;

		$result = array();

		foreach($stmt as $row)
			$result[] = $row;

		return $result;
	}

	public static function exec($query, $bindings = null)
	{
		if(!$stmt = self::query($query, $bindings))
			return false;

		return $stmt->rowCount();
	}

	public static function insert($query, $bindings = null)
	{
		if(!$stmt = self::query($query, $bindings))
			return false;

		return self::$pdo->lastInsertId();
	}

	public static function getLastError()
	{
		$error_info = self::$last_stmt->errorInfo();

		if($error_info[0] == 00000)
			return false;

		return $error_info;
	}

	public static function __callStatic($name, $arguments)
	{
		return call_user_func_array(
			array(self::instance(), $name),
			$arguments
		);
	}
}