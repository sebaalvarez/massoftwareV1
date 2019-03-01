--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

-- Started on 2019-01-10 14:49:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 8 (class 2615 OID 139062)
-- Name: massoftware; Type: SCHEMA; Schema: -; Owner: massoftwareroot
--

CREATE SCHEMA massoftware;


ALTER SCHEMA massoftware OWNER TO massoftwareroot;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 2 (class 3079 OID 16427)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2233 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET search_path = massoftware, pg_catalog;

--
-- TOC entry 221 (class 1255 OID 139090)
-- Name: ftgformatbanco(); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION ftgformatbanco() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
   
	-- NEW.comentario := massoftware.white_is_null(REPLACE(TRIM(NEW.comentario), '"', ''));	
    NEW.id := massoftware.white_is_null(NEW.id);
    NEW.numero := massoftware.zero_is_null(NEW.numero);
    NEW.nombre := massoftware.white_is_null(NEW.nombre);
    NEW.cuit := massoftware.zero_is_null(NEW.cuit);
    NEW.hoja := massoftware.zero_is_null(NEW.hoja);
    NEW.primeraFila := massoftware.zero_is_null(NEW.primeraFila);
    NEW.ultimaFila := massoftware.zero_is_null(NEW.ultimaFila);
    NEW.fecha := massoftware.white_is_null(NEW.fecha);
    NEW.descripcion := massoftware.white_is_null(NEW.descripcion);
    NEW.referencia1 := massoftware.white_is_null(NEW.referencia1);
    NEW.importe := massoftware.white_is_null(NEW.importe);
    NEW.referencia2 := massoftware.white_is_null(NEW.referencia2);
    NEW.saldo := massoftware.white_is_null(NEW.saldo);        

	RETURN NEW;
END;
$$;


ALTER FUNCTION massoftware.ftgformatbanco() OWNER TO postgres;

--
-- TOC entry 225 (class 1255 OID 139174)
-- Name: ftgformatcuentafondo(); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION ftgformatcuentafondo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
   
	-- NEW.comentario := massoftware.white_is_null(REPLACE(TRIM(NEW.comentario), '"', ''));	
    NEW.id := massoftware.white_is_null(NEW.id);
    NEW.cuentaFondoGrupo := massoftware.white_is_null(NEW.cuentaFondoGrupo);
    NEW.numero := massoftware.zero_is_null(NEW.numero);
    NEW.nombre := massoftware.white_is_null(NEW.nombre);
    NEW.cuentaFondoTipo := massoftware.white_is_null(NEW.cuentaFondoTipo);
    NEW.banco := massoftware.white_is_null(NEW.banco);
    
	RETURN NEW;
END;
$$;


ALTER FUNCTION massoftware.ftgformatcuentafondo() OWNER TO postgres;

--
-- TOC entry 223 (class 1255 OID 139126)
-- Name: ftgformatcuentafondogrupo(); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION ftgformatcuentafondogrupo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
   
	-- NEW.comentario := massoftware.white_is_null(REPLACE(TRIM(NEW.comentario), '"', ''));	
    NEW.id := massoftware.white_is_null(NEW.id);
    NEW.cuentaFondoRubro := massoftware.white_is_null(NEW.cuentaFondoRubro);
    NEW.numero := massoftware.zero_is_null(NEW.numero);
    NEW.nombre := massoftware.white_is_null(NEW.nombre);    
    
	RETURN NEW;
END;
$$;


ALTER FUNCTION massoftware.ftgformatcuentafondogrupo() OWNER TO postgres;

--
-- TOC entry 222 (class 1255 OID 139106)
-- Name: ftgformatcuentafondorubro(); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION ftgformatcuentafondorubro() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
   
	-- NEW.comentario := massoftware.white_is_null(REPLACE(TRIM(NEW.comentario), '"', ''));	
    NEW.id := massoftware.white_is_null(NEW.id);
    NEW.numero := massoftware.zero_is_null(NEW.numero);
    NEW.nombre := massoftware.white_is_null(NEW.nombre);    
    
	RETURN NEW;
END;
$$;


ALTER FUNCTION massoftware.ftgformatcuentafondorubro() OWNER TO postgres;

--
-- TOC entry 224 (class 1255 OID 139142)
-- Name: ftgformatcuentafondotipo(); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION ftgformatcuentafondotipo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
   
	-- NEW.comentario := massoftware.white_is_null(REPLACE(TRIM(NEW.comentario), '"', ''));	
    NEW.id := massoftware.white_is_null(NEW.id);
    NEW.numero := massoftware.zero_is_null(NEW.numero);
    NEW.nombre := massoftware.white_is_null(NEW.nombre);    
    
	RETURN NEW;
END;
$$;


ALTER FUNCTION massoftware.ftgformatcuentafondotipo() OWNER TO postgres;

--
-- TOC entry 220 (class 1255 OID 139068)
-- Name: translate(character varying); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION translate(att_val character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF CHAR_LENGTH(TRIM(att_val)) = 0 THEN
	
		RETURN null::VARCHAR;
	END IF;

	RETURN TRANSLATE(att_val, massoftware.translate_from (), massoftware.translate_to())::VARCHAR;
		
END;
$$;


ALTER FUNCTION massoftware.translate(att_val character varying) OWNER TO postgres;

--
-- TOC entry 218 (class 1255 OID 139066)
-- Name: translate_from(); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION translate_from() RETURNS character varying
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	RETURN '/\"'';,_-.âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ'::VARCHAR;
		
END;
$$;


ALTER FUNCTION massoftware.translate_from() OWNER TO postgres;

--
-- TOC entry 219 (class 1255 OID 139067)
-- Name: translate_to(); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION translate_to() RETURNS character varying
    LANGUAGE plpgsql
    AS $$
BEGIN
	
	RETURN '         aaaaaaaaaAAAAAAAAAeeeeeeeeeeEEEEEEEEEiiiiiiiiIIIIIIIIooooooooOOOOOOOOuuuuuuuuUUUUUUUUcCnN'::VARCHAR;
		
END;
$$;


ALTER FUNCTION massoftware.translate_to() OWNER TO postgres;

--
-- TOC entry 215 (class 1255 OID 139063)
-- Name: white_is_null(character varying); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION white_is_null(att_val character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF CHAR_LENGTH(TRIM(att_val)) = 0 THEN
	
		RETURN null::VARCHAR;
	END IF;

	RETURN TRIM(att_val)::VARCHAR;
		
END;
$$;


ALTER FUNCTION massoftware.white_is_null(att_val character varying) OWNER TO postgres;

--
-- TOC entry 216 (class 1255 OID 139064)
-- Name: zero_is_null(integer); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION zero_is_null(att_val integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF att_val = 0 THEN
	
		RETURN null::INTEGER;
	END IF;

	RETURN att_val::INTEGER;
		
END;
$$;


ALTER FUNCTION massoftware.zero_is_null(att_val integer) OWNER TO postgres;

--
-- TOC entry 217 (class 1255 OID 139065)
-- Name: zero_is_null(bigint); Type: FUNCTION; Schema: massoftware; Owner: postgres
--

CREATE FUNCTION zero_is_null(att_val bigint) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF att_val = 0 THEN
	
		RETURN null::BIGINT;
	END IF;

	RETURN att_val::BIGINT;
		
END;
$$;


ALTER FUNCTION massoftware.zero_is_null(att_val bigint) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 139069)
-- Name: banco; Type: TABLE; Schema: massoftware; Owner: postgres
--

CREATE TABLE banco (
    id character varying DEFAULT public.uuid_generate_v4() NOT NULL,
    numero integer NOT NULL,
    nombre character varying NOT NULL,
    cuit bigint,
    bloqueado boolean DEFAULT false NOT NULL,
    hoja integer,
    primerafila integer,
    ultimafila integer,
    fecha character varying(3),
    descripcion character varying(3),
    referencia1 character varying(3),
    importe character varying(3),
    referencia2 character varying(3),
    saldo character varying(3),
    CONSTRAINT banco_cuit_chk CHECK ((char_length(((cuit)::character varying)::text) = 11)),
    CONSTRAINT banco_hoja_check CHECK ((hoja > 0)),
    CONSTRAINT banco_nombre_chk CHECK ((char_length((nombre)::text) >= 2)),
    CONSTRAINT banco_numero_chk CHECK ((numero > 0)),
    CONSTRAINT banco_primerafila_check CHECK ((primerafila > 0)),
    CONSTRAINT banco_ultimafila_check CHECK ((ultimafila > 0))
);


ALTER TABLE banco OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 139144)
-- Name: cuentafondo; Type: TABLE; Schema: massoftware; Owner: postgres
--

CREATE TABLE cuentafondo (
    id character varying DEFAULT public.uuid_generate_v4() NOT NULL,
    cuentafondogrupo character varying NOT NULL,
    numero integer NOT NULL,
    nombre character varying NOT NULL,
    cuentafondotipo character varying NOT NULL,
    banco character varying,
    bloqueado boolean DEFAULT false NOT NULL,
    CONSTRAINT cuentafondo_nombre_chk CHECK ((char_length((nombre)::text) >= 1)),
    CONSTRAINT cuentafondo_numero_chk CHECK ((numero > 0))
);


ALTER TABLE cuentafondo OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 139108)
-- Name: cuentafondogrupo; Type: TABLE; Schema: massoftware; Owner: postgres
--

CREATE TABLE cuentafondogrupo (
    id character varying DEFAULT public.uuid_generate_v4() NOT NULL,
    cuentafondorubro character varying NOT NULL,
    numero integer NOT NULL,
    nombre character varying NOT NULL,
    CONSTRAINT cuentafondogrupo_nombre_chk CHECK ((char_length((nombre)::text) >= 2)),
    CONSTRAINT cuentafondogrupo_numero_chk CHECK ((numero > 0))
);


ALTER TABLE cuentafondogrupo OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 139092)
-- Name: cuentafondorubro; Type: TABLE; Schema: massoftware; Owner: postgres
--

CREATE TABLE cuentafondorubro (
    id character varying DEFAULT public.uuid_generate_v4() NOT NULL,
    numero integer NOT NULL,
    nombre character varying NOT NULL,
    CONSTRAINT cuentafondorubro_nombre_chk CHECK ((char_length((nombre)::text) >= 2)),
    CONSTRAINT cuentafondorubro_numero_chk CHECK ((numero > 0))
);


ALTER TABLE cuentafondorubro OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 139128)
-- Name: cuentafondotipo; Type: TABLE; Schema: massoftware; Owner: postgres
--

CREATE TABLE cuentafondotipo (
    id character varying DEFAULT public.uuid_generate_v4() NOT NULL,
    numero integer NOT NULL,
    nombre character varying NOT NULL,
    CONSTRAINT cuentafondotipo_nombre_chk CHECK ((char_length((nombre)::text) >= 2)),
    CONSTRAINT cuentafondotipo_numero_chk CHECK ((numero > 0))
);


ALTER TABLE cuentafondotipo OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- TOC entry 187 (class 1259 OID 27294)
-- Name: xxx; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE xxx (
    aaa "char"
);


ALTER TABLE xxx OWNER TO postgres;

SET search_path = massoftware, pg_catalog;

--
-- TOC entry 2221 (class 0 OID 139069)
-- Dependencies: 188
-- Data for Name: banco; Type: TABLE DATA; Schema: massoftware; Owner: postgres
--

COPY banco (id, numero, nombre, cuit, bloqueado, hoja, primerafila, ultimafila, fecha, descripcion, referencia1, importe, referencia2, saldo) FROM stdin;
10	10	LLOYDS BANK (BLSA) LIMITED	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
11	11	BANCO DE LA NACION ARGENTINA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
113	113	BANCO DE LA EDIFICADORA DE OLAVARRIA SA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
137	137	BANCO EMPRESARIO DE TUCUMAN COOP. LTDO.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
14	14	BANCO DE LA PROVINCIA DE BUENOS AIRES	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
141	141	BANCO SANTAFESINO DE INVER. Y DES. S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
147	147	BANCO B.I. CREDITANSTALT S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
148	148	BANCO MUNICIPAL DE LA PLATA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
149	149	COFIRENE BANCO DE INVERSION S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
15	15	ICBC - BANCO CHINO	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
150	150	HSBC BANCO ROBERTS S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
153	153	BANCO GENERAL DE NEGOCIOS S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
162	162	BANCO MAYO COOPERATIVO LIMITADO	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
165	165	MORGAN GUARANTY TRUST COMPANY NEW YORK	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
17	17	BANCO FRANCES S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
178	178	BANCO DE BALCARCE COOPERATIVO LIMITADA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
179	179	BANCO ALMAFUERTE COOPERATIVO LIMITADO	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
18	18	THE BANK OF TOKYO-MITSUBISHI, LTD.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
191	191	BANCO CREDICOOP COOPERATIVO LIMITADO	\N	f	10	2	50	c	D	E	J	H	I
198	198	BANCO DE VALORES S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
20	20	BANCO DE LA PROVINCIA DE CORDOBA	\N	f	9	3	891	B	A	C	D	E	I
229	229	BANCO DEL BUEN AYRE S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
231	231	BANCO DE RIO TERCERO COOP. LTDO.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
236	236	BANCO DO ESTADO DE SAO PAULO S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
247	247	BANCO ROELA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
254	254	BANCO MARIVA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
255	255	BANCO DEL SUQUIA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
256	256	CITICORP BCO DE INVERSION SA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
259	259	BANCO ITAU ARGENTINA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
260	260	BANCO EXTERIOR DE AMERICA S. A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
262	262	BANK OF AMERICA NAT. TRUST AND SAVING	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
265	265	H.S.B.C. -ex LAVORO	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
266	266	BANQUE NATIONALE DE PARIS	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
267	267	REPUBLIC NATIONAL BANK OF NEW YORK	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
268	268	BANCO PROVINCIA DE TIERRA DEL FUEGO	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
269	269	BANCO DE LA REP. ORIENTAL DEL URUGUAY	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
27	27	BANCO SUPERVIELLE SOCIETE GENERALE SA	\N	f	4	2	4	a	b	c	\N	e	f
271	271	BANCO VELOX S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
275	275	BANCO REPUBLICA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
277	277	BANCO SAENZ S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
280	280	BANCO FLORENCIA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
281	281	BANCO LINIERS SUDAMERICANO S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
285	285	BANCO MACRO MISIONES S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
29	29	BANCO DE LA CIUDAD DE BUENOS AIRES	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
293	293	BANCO MERCURIO S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
294	294	ING BANK N.V.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
295	295	AMERICAN EXPRESS BANK LTD. S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
297	297	BANCO  BANEX S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
299	299	BANCO COMAFI SOCIEDAD ANONIMA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
300	300	BANCO DE INVERSION Y COMERCIO EXTERIOR S	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
301	301	BANCO PIANO S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
303	303	BANCO FINANSUR S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
305	305	BANCO JULIO SOCIEDAD ANONIMA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
306	306	BANCO PRIVADO DE INVERSIONES S A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
308	308	BANCO TRANSANDINO S. A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
309	309	NUEVO BANCO DE LA RIOJA SOCIEDAD ANONIMA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
310	310	BANCO DEL SOL S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
311	311	NUEVO BANCO DEL CHACO S. A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
312	312	MBA BANCO DE INVERSIONES S. A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
314	314	BANCO BISEL SOCIEDAD ANONIMA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
315	315	BANCO DE FORMOSA SA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
317	317	BANCO DE RIO NEGRO S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
318	318	BANCO DE SALTA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
319	319	CORPORACION METROPOLITANA DE FINANZAS SA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
320	320	BANCO SAN LUIS S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
321	321	BANCO DE SANTIAGO DEL ESTERO S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
322	322	NUEVO BANCO INDUSTRIAL DE AZUL S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
323	323	CORP BANCA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
324	324	BANCO DE JUJUY S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
325	325	DEUTSCHE BANK S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
326	326	MERCOBANK S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
327	327	BANCO SAN MIGUEL DE TUCUMAN SA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
328	328	BANCO MENDOZA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
329	329	KOOKMIN BANK SUC. BS. AS.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
330	330	NUEVO BANCO DE SANTA FE SA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
331	331	BANCO CETELEM ARGENTINA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
34	34	BANCO PATAGONIA- ex MERCANTIL ARG.SA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
341	341	BMV BANCO MASVENTAS	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
386	386	NUEVO BANCO DE ENTRE RIOS S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
387	387	NUEVO BANCO SUQUIA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
388	388	NUEVO BANCO BISEL	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
389	389	BANCO COLUMBIA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
39	39	BANCO CAJA DE AHORRO SOCIEDAD ANONIMA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
42	42	THE CHASE MANHATTAN BANK BUENOS AIRES	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
426	426	BICA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
43	43	BANCO QUILMES S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
431	431	BANCO COINAG	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
44	44	BANCO HIPOTECARIO NACIONAL	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
45	45	BANCO DE SAN JUAN S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
46	46	BANCO DO BRASIL S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
5	5	ABN AMRO BANK N. V.	55555555555	t	1	2	3	4	5	6	7	8	9
50	50	BANCO TORNQUIST SOCIEDAD ANONIMA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
54	54	BANCO COMERCIAL ISRAELITA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
59	59	BANCO DE ENTRE RIOS S. A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
6	6	BANCO SUDAMERIS ARGENTINA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
60	60	BANCO TUCUMAN GRUPO MACRO	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
62	62	BANCO ISRAELITA DE CORDOBA S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
65	65	BANCO MUNICIPAL DE ROSARIO	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
67	67	BANCO BANSUD S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
7	7	BANCO DE GALICIA Y BUENOS AIRES S.A.	\N	f	1	2	693	a	b	c	\N	f	g
72	72	BANCO SANTANDER RIO -	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
777	777	777	77777777777	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
79	79	BANCO REGIONAL DE CUYO S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
81	81	BANCO SOCIAL DE CORDOBA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
83	83	BANCO DEL CHUBUT S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
86	86	BANCO DE LA PROVINCIA DE SANTA CRUZ S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
889	889	889	13213213213	t	254	56	98	12	65	89	dfs	dfd	sdf
891	891	PATAGONIAS	45465465465	t	255	8	16	A1	A3	A6	\N	A6	B8
892	892	PATAGONIAS2	88888899888	t	255	8	16	A1	A3	A6	\N	A6	B8
92	92	BANCO DE CATAMARCA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
93	93	BANCO DE LA PAMPA	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
94	94	BANCO DE CORRIENTES S.A.	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
97	97	BANCO DE LA PROVINCIA DEL NEUQUEN	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
3	3	BANCO EUROPEO P/AMERICA LATINA	20245465465	f	\N	\N	\N	\N	\N	\N	\N	\N	\N
23066f2a-277a-404b-9be9-ad91908a8985	894	ddddddsdf	23433333333	t	2	3	3	3	3	3	3	3	3
b310d6af-3268-471c-8a3d-47ff589fd32c	893	dddddd	23432423423	t	2	3	3	3	3	3	3	44	3
\.


--
-- TOC entry 2225 (class 0 OID 139144)
-- Dependencies: 192
-- Data for Name: cuentafondo; Type: TABLE DATA; Schema: massoftware; Owner: postgres
--

COPY cuentafondo (id, cuentafondogrupo, numero, nombre, cuentafondotipo, banco, bloqueado) FROM stdin;
1	1-1	1	1	2	\N	t
3	1-40	3	3	3	\N	f
22	1-40	22	222	3	\N	f
444	1-1	444	444	2	3	f
666	1-1	666	666	2	\N	f
2222	1-2	2222	2222	1	\N	f
11101	1-1	11101	Pesos (VM)	1	\N	f
11102	1-1	11102	Dolares  (NO USAR)	5	5	f
11103	1-1	11103	Patacones  ( NO USAR)	1	5	f
11104	1-1	11104	LECOP ( NO USAR)	5	5	f
11105	1-1	11105	Pesos (Ros)	5	\N	f
11106	1-1	11106	Pesos (Rol)	1	5	f
11107	1-1	11107	Pesos Vale	1	\N	f
11108	1-1	11108	Pesos Agrícola	1	\N	f
11150	1-1	11150	Tickets	1	\N	f
11180	1-1	11180	Canje (Ventas-Compras)	1	\N	f
11190	1-50	11190	XDiferencia Cotizacion Moneda Ext.	5	5	f
11201	1-5	11201	XBanco de la Nación Argentina	2	5	f
11202	1-5	11202	Banco de la Nación Argentina  Diferidos	2	11	f
11203	1-10	11203	XBanco Nación C.A.u$s	2	5	f
11204	1-5	11204	XBanco de la Nación Argentina Lecop	2	5	f
11205	1-5	11205	XBanco Macro SA 1002/2	2	5	f
11206	1-5	11206	Banco Macro SA Diferido	2	5	f
11207	1-5	11207	Banco Cordoba 7190/1 **	2	\N	f
11208	1-5	11208	XBco Macro - Suc.P. de la Villa 2220/07	2	5	f
11209	1-5	11209	Banco Galicia   cta cte $ **	2	\N	f
11210	1-5	11210	Banco Galica cta cte. U$S	5	\N	f
11211	1-5	11211	XBanco Macro SA 2187/3 $  Rosario	2	5	f
11212	1-5	11212	XBanco Macro SA 1368/7 U$S	2	5	f
11213	1-5	11213	XBanco Bisel 10131/08  C.A.U$S	2	\N	f
11214	1-10	11214	XBanco Cordoba  C.Ahorro U$S	2	5	f
11215	1-5	11215	Banco Córdoba Diferido **	2	20	f
11216	1-5	11216	Banco Macro - Suc.P. de la Villa	2	5	f
11217	1-5	11217	Banco Macro SA 2187/3  Diferido-Rosario	2	255	f
11218	1-5	11218	Banco Galicia Diferido **	2	7	f
11219	1-5	11219	XBanco Córdoba Lecop cta.cte.7190/21	2	20	f
11220	1-5	11220	Banco Córdoba Lecop- Diferido 7190/21	2	20	f
11221	1-5	11221	XBanco Boston   cta cte	2	15	f
11222	1-5	11222	Banco Boston  Diferido	2	5	f
11223	1-5	11223	Banco Crediccop   cta cte **	2	191	f
11224	1-5	11224	Banco Crediccop  Diferido **	2	191	f
11225	1-5	11225	Banco Santander Río  cta cte **	2	72	f
11226	1-5	11226	X Banco Río   Diferido	2	5	f
11227	1-5	11227	XBanco Francés cta cte - 2731/3 -	2	5	f
11228	1-5	11228	Banco Francés Diferido - 2731/3 -	2	5	f
11229	1-5	11229	Banco Santander Río Diferido **	2	72	f
11230	1-5	11230	X Banco Río  cta cte	2	5	f
11231	1-5	11231	XBanco Macro $ 207629/3	2	5	f
11232	1-5	11232	XBanco Macro u$s 207629/3	2	5	f
11233	1-5	11233	XNuevo Banco Santa Fé Cta. Cte.	2	5	f
11234	1-5	11234	Nuevo Banco Sta Fé Diferido	2	5	f
11235	1-5	11235	XBanco John Deere Credit U$S	2	5	f
11236	1-5	11236	Banco Supervielle	2	27	f
11237	1-5	11237	Banco Supervielle Diferido	2	27	f
11238	1-5	11238	Banco Galicia Rosario	2	7	f
11239	1-5	11239	Banco Galicia Diferido Rosario	2	7	f
11301	1-2	11301	Valores Pendientes Banco Suquía	3	\N	f
11400	25-100	11400	Valores Remesados Rosario	5	\N	f
11401	1-2	11401	Valores al Cobro (VM)	3	\N	f
11402	25-100	11402	Valores Remesados Roldan	5	\N	f
11403	1-2	11403	Valores al Cobro Agricola	3	\N	f
11404	1-2	11404	Valores al Cobro (A)	3	\N	f
11405	1-2	11405	Valores al Cobro (R)	3	\N	f
11406	25-100	11406	Movimiento Valores Custodia (p)	5	\N	f
11407	1-1	11407	Moneda Extranjera - Dólares -	5	\N	f
11408	1-5	11408	Moneda Extranjera - Euros-	5	\N	f
11409	25-100	11409	Transferencia Bancaria	5	\N	f
11500	1-1	11500	Pendientes (Contrareembolso)	5	\N	f
11501	1-2	11501	Valores Rechazados	3	\N	f
11502	25-999	11502	X VALORES RECHAZADOS (No Usar)	5	\N	f
11503	10-1	11503	Cereales Canje	5	\N	f
11504	10-1	11504	Ventas con Canje Cereal	5	\N	f
11505	10-1	11505	Cerealera Pago Diferido	5	\N	f
11507	15-1	11507	Pendientes ( Taller)	5	\N	f
11508	15-1	11508	Pendientes ( Varios)	5	\N	f
11509	15-1	11509	Pendientes (TAVIAN)	5	\N	f
11510	1-2	11510	Valores - Bolsa Silo - (VM)	3	\N	f
11511	1-2	11511	Valores - Bolsa Silo - (A)	3	\N	f
11608	25-999	11608	X Cupones	4	\N	f
11609	1-30	11609	Tarjeta Credencial	4	\N	f
11610	1-30	11610	Tarjeta Santa Fé AGRO	4	\N	f
11697	1-30	11697	Tarjeta Galicia Rural	4	\N	f
11699	1-30	11699	Tarjeta Maestro	4	\N	f
11700	1-30	11700	Tarjeta Vycard	4	\N	f
11701	1-30	11701	Tarjeta Naranja	4	\N	f
11702	1-30	11702	Tarjeta Provencred	4	\N	f
11703	1-30	11703	Tarjeta Visa	4	\N	f
11704	1-30	11704	Tarjeta Mastercard	4	\N	f
11705	1-30	11705	Tarjeta Plan Platino	4	\N	f
11706	1-30	11706	Tarjeta Azul	4	\N	f
11707	1-30	11707	Tarjeta Agronación	4	\N	f
11708	1-30	11708	Tarjeta Visa Electron	4	\N	f
11709	1-30	11709	Tarjeta Bisel Agro	4	\N	f
11710	25-999	11710	X Banco Bisel Cupones	5	\N	f
11711	25-999	11711	X Diners Cupones	5	\N	f
11712	25-999	11712	X Getres Cupones	5	\N	f
11713	25-999	11713	X Banco Nación  C upón Dólares	5	\N	f
11714	1-30	11714	Tarjeta Macro Agro	4	\N	f
11801	1-40	11801	Documentos a Cobrar	1	\N	f
11803	1-50	11803	Inversión Plazo Fijo	5	\N	f
11804	1-50	11804	Intereses Plazos Fijos	5	\N	f
11805	5-5	11805	Interplan SA	5	5	f
12202	5-2	12202	AFIP - Retenciones Ganancias RG.2784	5	\N	f
12204	5-2	12204	Retención S.U S.S.	5	5	f
12205	5-2	12205	DGR - Retenciones Ingresos Brutos	5	\N	f
12207	5-2	12207	AFIP - Retención Iva	5	\N	f
12208	5-2	12208	Percepción IVA	5	\N	f
12209	25-999	12209	X Percepción IVA no Categorizado	5	\N	f
12210	5-2	12210	Retención Ing.Btos. realizada	5	\N	f
12211	5-2	12211	Ret.Ganancias  realizada	5	\N	f
12212	5-2	12212	Ret. IVA a Pagar	5	\N	f
12213	5-2	12213	Ret. Ing. Brutos Realizada-Santa Fe	5	\N	f
12214	5-2	12214	Retenciones Ingresos Brutos Santa Fe	5	\N	f
12501	10-5	12501	Deudores en Gestión Judicial	5	\N	f
12502	10-5	12502	Deudores Incobrables	5	\N	f
12505	25-999	12505	X Anticipo por Adq.Bienes Capital	5	\N	f
12506	25-999	12506	X Anticipo por Compra de Mercaderías	5	\N	f
12510	5-1	12510	Cta. Particular - Lorenzo Gorno	5	\N	f
12511	5-1	12511	Cta. Particular - Carlos Nicoletti	5	\N	f
12512	5-1	12512	Cta.Particular - Jose Felipe	5	\N	f
12513	5-1	12513	Cta. Particular - Raquel Felipe	5	\N	f
12514	5-5	12514	Reintegro Seguros	5	\N	f
12515	10-5	12515	Reintegro Anses	5	\N	f
12516	25-100	12516	Transf. JD	5	\N	f
12517	10-5	12517	Reintegro Seguro ART	5	5	f
13101	10-1	13101	Deudores por Ventas	5	\N	f
13201	10-5	13201	Renta por Otras Inversiones	5	\N	f
13203	10-5	13203	Ingresos Varios	5	\N	f
13204	25-999	13204	X Otros Ingresos Loteo	5	\N	f
13205	25-999	13205	Diferencias de Caja	5	\N	f
13206	1-50	13206	Deuda Moneda Extranjera	5	\N	f
13207	15-5	13207	Señas de Clientes	5	\N	f
13209	15-5	13209	Anticipos de Clientes JD	5	\N	f
13210	5-5	13210	Maquinaria en Tránsito a Cobrar	5	\N	f
13211	25-100	13211	Fact.de Terceros IJD	5	\N	f
13212	10-5	13212	Acreedores Prendarios	5	\N	f
13213	10-5	13213	Reintegro Tarj. VISA Agro-RL	5	5	f
13214	10-5	13214	LBO Valores	5	\N	f
13215	10-5	13215	Tarjetas John Deere	4	\N	f
14001	15-5	14001	Proveedores Cuenta Corriente	5	\N	f
14002	15-10	14002	Acreedores Vs.	5	\N	f
14003	15-5	14003	XX  PERKINS Motores V.M.	5	5	f
14004	15-5	14004	PERKINS ARGENTINA S.A.I.C.	5	\N	f
14005	15-5	14005	MAINERO	5	\N	f
14006	15-5	14006	RALUX  S.A.I.C.	5	\N	f
14007	15-5	14007	SHER S.R.L.	5	\N	f
14008	15-5	14008	LA SEGUNDA - SEGUROS	5	\N	f
14009	15-5	14009	MAPFRE - SEGUROS	5	\N	f
14010	15-1	14010	X MASSLIFE -seguros	5	\N	f
14011	15-10	14011	Acreedores Ocasionales	5	\N	f
14013	5-2	14013	Felipe Raquel Retiros y Pagos a Cta.	1	\N	f
14015	5-2	14015	Gorno Lorenzo Retiros  y Pagos A Cta.	1	\N	f
14016	5-5	14016	Total Lubricantes Argentina SA	5	5	f
14017	15-5	14017	Seguro Mercantil (La Segunda)	5	\N	f
22000	25-999	22000	X Compra  Tractores	5	\N	f
22001	25-999	22001	X Conserv.y Mant.Maquinas y Equipos	5	\N	f
22002	25-999	22002	X Conserv.y Mant.Edificio	5	\N	f
22003	25-999	22003	X Gastos Reposicion Herramientas	5	\N	f
22004	25-999	22004	X Conserv.y Mant.Rodados	5	\N	f
22005	25-999	22005	X Compra de Repuestos	5	\N	f
22006	25-999	22006	X Compra Motores	5	\N	f
22007	25-999	22007	X Compra Maquinarias	5	\N	f
22008	25-999	22008	X Rodados	5	\N	f
22009	25-999	22009	X Instalaciones	5	\N	f
22010	25-999	22010	X Muebles y Utiles	5	\N	f
22011	25-999	22011	X Maquinarias	5	\N	f
22012	25-999	22012	X Herramientas	5	\N	f
22013	25-999	22013	X Equipos de Computación	5	5	f
22014	25-999	22014	X Reparación  y Mant.Equipos Oficina	5	\N	f
22101	25-999	22101	X Servicios Técnicos	5	\N	f
22102	25-999	22102	X Mano de Obra de Terceros	5	\N	f
23101	25-999	23101	X Intereses y Comisiones Bancarias	5	5	f
23102	25-999	23102	X Comisiones y Dif.de Cambio	5	\N	f
23104	25-999	23104	X Otros Intereses	5	\N	f
23106	25-999	23106	X Intereses Inv.Plazo Fijo	5	\N	f
23107	25-999	23107	X Intereses por Inversión	5	\N	f
23108	25-999	23108	X Canje Cereal	5	\N	f
23109	25-999	23109	X   Diferencia de Cotizacion	5	\N	f
23110	15-1	23110	Arancel	5	\N	f
23111	25-999	23111	X Intereses por Financiación	5	\N	f
24101	25-999	24101	X Impuesto Ley 25413   s/ DEBITO	5	\N	f
24102	25-999	24102	X Impuesto Ley 25413  s/ CREDITO	5	\N	f
24103	25-999	24103	I.A.M.	5	\N	f
24104	25-999	24104	Ingresos Brutos a Pagar	5	\N	f
24105	25-999	24105	Tasa Unificada-1838-	5	5	f
24106	25-999	24106	X Impuestos a  los Rodados	5	\N	f
24107	25-999	24107	X Impuestos  sobre Inmuebles	5	\N	f
24108	15-1	24108	Acreedores Leyes Sociales	5	\N	f
24109	25-999	24109	Sueldos a Pagar Villa María	5	5	f
24110	15-1	24110	Aportes Sociales  Ley  5110	5	5	f
24111	25-999	24111	IVA  a  Pagar	5	\N	f
24112	25-999	24112	X Imp.s/int.ley 25063	5	\N	f
24113	15-1	24113	Indemnización Personal	5	\N	f
24114	15-1	24114	Impuesto sobre los Bienes Personales	5	\N	f
24115	25-999	24115	Derecho Reg. Inspec. Rosario (DRei)	5	\N	f
24116	25-999	24116	Derecho Reg. Inspec. Roldan (DRei)	5	\N	f
24117	15-1	24117	Gastos Sellado Libro Sueldos	5	\N	f
24118	15-1	24118	Imp. sobre Carteles de Negocio	5	5	f
25101	25-999	25101	X Primas de Seguros	5	\N	f
26001	25-999	26001	X Energia	5	\N	f
27000	15-1	27000	PERDIDA POR ROBO	5	5	f
27001	25-999	27001	X Material Embalaje	5	\N	f
27002	25-999	27002	X Elementos de Limpieza	5	\N	f
27003	25-999	27003	X Fletes y Acarreos	5	\N	f
27004	25-999	27004	X Gastos Generales	5	\N	f
27005	25-999	27005	X Papelería y Utiles de Oficina	5	\N	f
27006	25-999	27006	X Honorarios Profesionales	5	\N	f
27007	25-999	27007	X Franqueos  y Timbres Postales	5	\N	f
27008	25-999	27008	X Impresos	5	\N	f
27009	25-999	27009	X Gastos Administrativos y Formulario	5	\N	f
27010	25-999	27010	X Viaticos viajantes	5	\N	f
27011	25-999	27011	X Viaticos agricolas	5	\N	f
27012	25-999	27012	X Refrigerio	5	\N	f
27013	25-999	27013	X Viaticos Servicio	5	\N	f
27014	25-999	27014	X Reparación  y Mant. Máquinas Taller	5	\N	f
27015	25-999	27015	X Reparación y Mant. Rodados Taller	1	\N	f
27016	25-999	27016	X Insumo Taller	5	\N	f
27017	25-999	27017	X Combustible Taller	5	\N	f
27018	25-999	27018	X Gastos Vs. Menores Taller	5	\N	f
27019	25-999	27019	X Gastos Bancarios	5	\N	f
27021	25-999	27021	X Reparación y Mant.Maq. Inyección	5	\N	f
27022	25-999	27022	X Viáticos Inyección	5	\N	f
27023	25-999	27023	X Insumo Inyección	5	\N	f
27024	25-999	27024	X Combustible Inyección	5	\N	f
27025	25-999	27025	X Mano de Obra Inyección	5	\N	f
27026	25-999	27026	X Gastos Vs. Menores Inyecci´on	5	\N	f
27027	25-999	27027	X Mano Obra Terceros	5	\N	f
27028	25-999	27028	X Mano Obra  Agrícola	5	\N	f
27030	25-999	27030	X Contribuciones y Suscripciones	5	\N	f
27031	25-999	27031	X Reparación y Mant.Rodados Viajantes	5	\N	f
27032	25-999	27032	X Combustibles Viajantes	5	\N	f
27033	25-999	27033	X Gastos Vs.Menores Viajantes	5	\N	f
27034	25-999	27034	X Publicidad	5	\N	f
27035	25-999	27035	X Homenajes y Agasajos	5	\N	f
27036	25-999	27036	X Reparación y Mant.Rodados Comercial.	5	\N	f
27037	25-999	27037	X Combustibles Comercialización	5	\N	f
27038	25-999	27038	X Gatos Vs. Menores Comercialización	5	\N	f
27041	25-999	27041	X Reparación y Mant. Inmuebles	5	\N	f
27042	25-999	27042	X Gastos Operativos	5	\N	f
27043	25-999	27043	X Gastos Representación	5	\N	f
27044	25-999	27044	X Gastos Exposición	5	\N	f
27048	25-999	27048	X Publicom	5	\N	f
27049	25-999	27049	X Posnet	5	\N	f
27050	25-999	27050	X Gas	5	\N	f
27051	25-999	27051	X Telecom Personal	5	\N	f
27052	25-999	27052	X Teléfono	5	\N	f
27053	15-1	27053	X Advance	5	\N	f
27054	25-999	27054	X Decidir	5	\N	f
27055	25-999	27055	X Nextel	5	\N	f
27056	25-999	27056	X C.N.T.	5	\N	f
27057	25-999	27057	X Gastos Constitución Prendas	5	\N	f
27058	25-999	27058	X Costas Judiciales	5	\N	f
27060	25-999	27060	Intereses Deuda Moneda Extranjera	5	\N	f
27061	25-999	27061	X Inversión Moneda Extranjera	5	\N	f
27062	25-999	27062	X Gastos Liquidación Cereales	5	\N	f
27063	25-999	27063	X Insumo Computación	5	\N	f
27064	25-999	27064	X Gastos Varios Menores Computación	5	\N	f
27065	25-999	27065	X Gastos Varios Comunes	5	\N	f
27070	25-999	27070	X Mantenimiento Rodados Div.AGrícola	5	\N	f
27072	25-999	27072	X Publicidad Div. Agrícola	5	\N	f
27073	25-999	27073	Seguros Div. Agrícola	5	5	f
27074	25-999	27074	X Terreno División Agrícola	5	\N	f
27075	25-999	27075	X Mano Obra Div.Agrícola	5	\N	f
27076	25-999	27076	X Gastos Maquinarias Agrícolas	5	\N	f
28000	25-999	28000	X Comisión vta.Repuestos	5	5	f
28001	25-999	28001	X Comisión Vta. Motores	5	5	f
28002	25-999	28002	X Comisión Vta. Maquinarias	5	\N	f
28003	25-999	28003	X Comisión Vta. Tractores	5	5	f
28020	25-999	28020	X Gastos Edificio Roldán	5	\N	f
28021	25-999	28021	X Pavimento Vetaro	5	\N	f
28022	25-999	28022	X Alquileres	5	\N	f
28023	25-999	28023	X Alquiler Fotocopiadora	5	\N	f
28024	25-999	28024	X Canon Leasing	5	\N	f
28025	25-999	28025	X Gastos Leasing	5	\N	f
28026	25-999	28026	X Toyota Credit S.A.	5	\N	f
28500	25-999	28500	X Proveedores - Rosario	5	\N	f
28501	15-5	28501	XX  PERKINS REPUESTOS - Rosario	5	5	f
28502	15-5	28502	XX  PERKINS MOTORES - Rosario	5	5	f
28503	25-999	28503	X PRESTOLITE - Rosario	5	\N	f
28504	15-5	28504	RALUX  -  Rosario	5	\N	f
28505	15-5	28505	SHER S.R.L. - Rosario	5	\N	f
28506	15-1	28506	X JOHN  DEERE REPUESTOS	5	\N	f
28507	15-5	28507	JOHN DEERE	5	\N	f
28508	15-5	28508	PRESTOLITE	5	\N	f
28509	15-5	28509	John Deere Anticipos	5	\N	f
28510	25-999	28510	X Alquileres  - Rosario	5	\N	f
28511	25-999	28511	Sueldos a Pagar Rosario	5	\N	f
28512	15-5	28512	PRESTOLITE Indiel	5	5	f
28513	15-1	28513	X MOSAL -Rosario	5	\N	f
28514	15-5	28514	John Deere Iva Señas	5	\N	f
28515	15-5	28515	John Deere Maquinarias	5	\N	f
28516	25-999	28516	Sueldos a Pagar Roldan	5	\N	f
30001	15-1	30001	Bienes de Cambio	5	\N	f
30010	15-1	30010	Bienes de Uso	5	\N	f
30020	15-1	30020	Gastos Operativos	5	\N	f
30021	15-1	30021	XGastos Adm. Cobrados	5	5	f
30022	15-1	30022	Gastos Banc Cobrados ctado	5	\N	f
30023	15-1	30023	Gastos Bcrios Cobrados Ds.Vtas.	5	\N	f
30024	15-1	30024	Gastos Cobrados Adm. Ctado	5	\N	f
30030	15-1	30030	Gastos Financieros	5	\N	f
30060	15-1	30060	Gastos a Rendir	5	\N	f
30061	1-1	30061	Gastos Venta Bco. Córdoba	5	\N	f
30064	5-5	30064	Deposito Judicial	5	\N	f
30065	1-1	30065	Gastos a Rendir - N.G.	5	\N	f
30070	15-1	30070	Gastos a Procesar	5	\N	f
30071	15-1	30071	Gastos Valores Rechazados a Cobrar	5	\N	f
30072	15-1	30072	Obligaciones a Pagar	5	\N	f
30073	15-1	30073	Cta. Pmo. Banco Galicia	5	\N	f
30074	15-1	30074	Cta. Pmo. Banco Provincia de Córdoba	5	\N	f
30075	15-1	30075	Cta. Pmo. Banco Credicoop	5	\N	f
30076	15-1	30076	Cta. Pmo. Banco Frances	5	\N	f
30077	15-1	30077	Cta. Pmo. Banco Macro	5	\N	f
30078	15-1	30078	Visa Francés	5	5	f
30079	15-1	30079	Cta. Pmo. Banco Santander Río	5	5	f
30100	15-1	30100	XPrevisionales y Fiscales	5	5	f
30200	1-1	30200	Otras Erogaciones Varias	5	\N	f
60000	25-1	60000	Cuenta de Ajuste	5	\N	f
60010	25-1	60010	Ajuste Saldos Iniciales	5	5	f
60020	25-100	60020	Devolucion de Cheque defecto formal	5	\N	f
60024	1-1	60024	Valores Rechazados de Contado	5	\N	f
60025	25-100	60025	Devolución de Cheque	5	\N	f
60026	25-100	60026	Dev. valores Rechazados	5	\N	f
60030	15-1	60030	Ajuste de Saldo	5	\N	f
60031	15-1	60031	Cancelación de Anticipos	5	\N	f
60050	25-100	60050	Transferencia Roldan (A)	5	\N	f
60060	1-1	60060	Canje Acreedor	5	\N	f
60061	15-1	60061	Intereses por Descubiertos Bancarios	5	\N	f
60062	5-2	60062	Plan Pago AFIP	5	\N	f
60063	25-100	60063	Intervención p/operación John Deere	5	\N	f
60064	5-5	60064	Cuenta Pmo. por Compras CHN	5	\N	f
60065	5-5	60065	Cuenta Pmo. por Compras LAG	5	\N	f
60066	15-1	60066	Recargos Impositivos VM	5	5	f
60067	5-2	60067	Plan Pago II BB Sta Fe	5	\N	f
60068	15-1	60068	Intereses Impositivos	5	\N	f
60069	25-100	60069	Transferencia Vetaro - JD	5	\N	f
60071	25-999	60071	Sueldos a Pagar JD	5	5	f
60072	5-2	60072	Retencion Contrib s/ Act Comerc Ind y Ss	5	\N	f
60073	1-1	60073	Bco Córdoba Valores Vendidos	1	\N	f
60080	15-1	60080	Cta. Pmo. Banco Supervielle	5	5	f
60082	5-1	60082	Cta. Particular - LG  FOLK	5	5	f
60083	5-1	60083	Cta. Particular - CN -ZAMPAR	5	5	f
\.


--
-- TOC entry 2223 (class 0 OID 139108)
-- Dependencies: 190
-- Data for Name: cuentafondogrupo; Type: TABLE DATA; Schema: massoftware; Owner: postgres
--

COPY cuentafondogrupo (id, cuentafondorubro, numero, nombre) FROM stdin;
10-1	10	1	Deudores por Ventas
10-5	10	5	Deudores Varios
1-1	1	1	Cajas
1-10	1	10	Bancos Caja de Ahorro
1-2	1	2	Cartera de Cheques
1-30	1	30	Tarjeta de Créditos
1-40	1	40	Documentos
1-5	1	5	Bancos Cuentas Corrientes
1-50	1	50	Inversiones
15-1	15	1	Varios
15-10	15	10	Otras Compras
15-5	15	5	Proveedores
25-1	25	1	Saldo Iniciales
25-100	25	100	Transferencia
25-999	25	999	Cuentas Fuera de Uso
5-1	5	1	Retiros Directores
5-2	5	2	Anticipos Fiscales
5-5	5	5	Otros Créditos
66-999	66	999	99
bc04aaf6-60ca-415b-b99e-25a98986304f	66	1000	999
2a0b2bf7-bd27-491e-9f83-46b1b7ebdf97	66	1001	Ordoñez
8ef8dcbe-d870-4205-aa78-12bdc17c6394	66	1002	asdasd
d2e49b66-a4e9-4ed7-a016-5665b8f6de56	66	1003	eeeeeeeeee
9443256b-67e7-4115-994b-a4215d03317e	66	1004	aa
\.


--
-- TOC entry 2222 (class 0 OID 139092)
-- Dependencies: 189
-- Data for Name: cuentafondorubro; Type: TABLE DATA; Schema: massoftware; Owner: postgres
--

COPY cuentafondorubro (id, numero, nombre) FROM stdin;
1	1	Disponibilidades
10	10	Ingresos
15	15	Egresos
25	25	Cuenta de Ajuste
5	5	Créditos
66	66	666
\.


--
-- TOC entry 2224 (class 0 OID 139128)
-- Dependencies: 191
-- Data for Name: cuentafondotipo; Type: TABLE DATA; Schema: massoftware; Owner: postgres
--

COPY cuentafondotipo (id, numero, nombre) FROM stdin;
1	1	Caja
2	2	Banco
3	3	Cartera
4	4	Tarjeta
5	5	Otra
6	6	Tickets
\.


SET search_path = public, pg_catalog;

--
-- TOC entry 2220 (class 0 OID 27294)
-- Dependencies: 187
-- Data for Name: xxx; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY xxx (aaa) FROM stdin;
\.


SET search_path = massoftware, pg_catalog;

--
-- TOC entry 2069 (class 2606 OID 139088)
-- Name: banco banco_cuit_key; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY banco
    ADD CONSTRAINT banco_cuit_key UNIQUE (cuit);


--
-- TOC entry 2071 (class 2606 OID 139086)
-- Name: banco banco_numero_key; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY banco
    ADD CONSTRAINT banco_numero_key UNIQUE (numero);


--
-- TOC entry 2073 (class 2606 OID 139084)
-- Name: banco banco_pkey; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- TOC entry 2090 (class 2606 OID 139157)
-- Name: cuentafondo cuentafondo_numero_key; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondo
    ADD CONSTRAINT cuentafondo_numero_key UNIQUE (numero);


--
-- TOC entry 2092 (class 2606 OID 139155)
-- Name: cuentafondo cuentafondo_pkey; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondo
    ADD CONSTRAINT cuentafondo_pkey PRIMARY KEY (id);


--
-- TOC entry 2081 (class 2606 OID 139118)
-- Name: cuentafondogrupo cuentafondogrupo_pkey; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondogrupo
    ADD CONSTRAINT cuentafondogrupo_pkey PRIMARY KEY (id);


--
-- TOC entry 2076 (class 2606 OID 139104)
-- Name: cuentafondorubro cuentafondorubro_numero_key; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondorubro
    ADD CONSTRAINT cuentafondorubro_numero_key UNIQUE (numero);


--
-- TOC entry 2078 (class 2606 OID 139102)
-- Name: cuentafondorubro cuentafondorubro_pkey; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondorubro
    ADD CONSTRAINT cuentafondorubro_pkey PRIMARY KEY (id);


--
-- TOC entry 2085 (class 2606 OID 139140)
-- Name: cuentafondotipo cuentafondotipo_numero_key; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondotipo
    ADD CONSTRAINT cuentafondotipo_numero_key UNIQUE (numero);


--
-- TOC entry 2087 (class 2606 OID 139138)
-- Name: cuentafondotipo cuentafondotipo_pkey; Type: CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondotipo
    ADD CONSTRAINT cuentafondotipo_pkey PRIMARY KEY (id);


--
-- TOC entry 2074 (class 1259 OID 139089)
-- Name: u_banco_nombre; Type: INDEX; Schema: massoftware; Owner: postgres
--

CREATE UNIQUE INDEX u_banco_nombre ON banco USING btree (translate(lower(btrim((nombre)::text)), '/\"'';,_-.âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ'::text, '         aaaaaaaaaAAAAAAAAAeeeeeeeeeeEEEEEEEEEiiiiiiiiIIIIIIIIooooooooOOOOOOOOuuuuuuuuUUUUUUUUcCnN'::text));


--
-- TOC entry 2093 (class 1259 OID 139173)
-- Name: u_cuentafondo_nombre; Type: INDEX; Schema: massoftware; Owner: postgres
--

CREATE UNIQUE INDEX u_cuentafondo_nombre ON cuentafondo USING btree (translate(lower(btrim((nombre)::text)), '/\"'';,_-.âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ'::text, '         aaaaaaaaaAAAAAAAAAeeeeeeeeeeEEEEEEEEEiiiiiiiiIIIIIIIIooooooooOOOOOOOOuuuuuuuuUUUUUUUUcCnN'::text));


--
-- TOC entry 2082 (class 1259 OID 139125)
-- Name: u_cuentafondogrupo_nombre; Type: INDEX; Schema: massoftware; Owner: postgres
--

CREATE UNIQUE INDEX u_cuentafondogrupo_nombre ON cuentafondogrupo USING btree (cuentafondorubro, translate(lower(btrim((nombre)::text)), '/\"'';,_-.âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ'::text, '         aaaaaaaaaAAAAAAAAAeeeeeeeeeeEEEEEEEEEiiiiiiiiIIIIIIIIooooooooOOOOOOOOuuuuuuuuUUUUUUUUcCnN'::text));


--
-- TOC entry 2083 (class 1259 OID 139124)
-- Name: u_cuentafondogrupo_rubro_grupo; Type: INDEX; Schema: massoftware; Owner: postgres
--

CREATE UNIQUE INDEX u_cuentafondogrupo_rubro_grupo ON cuentafondogrupo USING btree (cuentafondorubro, numero);


--
-- TOC entry 2079 (class 1259 OID 139105)
-- Name: u_cuentafondorubro_nombre; Type: INDEX; Schema: massoftware; Owner: postgres
--

CREATE UNIQUE INDEX u_cuentafondorubro_nombre ON cuentafondorubro USING btree (translate(lower(btrim((nombre)::text)), '/\"'';,_-.âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ'::text, '         aaaaaaaaaAAAAAAAAAeeeeeeeeeeEEEEEEEEEiiiiiiiiIIIIIIIIooooooooOOOOOOOOuuuuuuuuUUUUUUUUcCnN'::text));


--
-- TOC entry 2088 (class 1259 OID 139141)
-- Name: u_cuentafondotipo_nombre; Type: INDEX; Schema: massoftware; Owner: postgres
--

CREATE UNIQUE INDEX u_cuentafondotipo_nombre ON cuentafondotipo USING btree (translate(lower(btrim((nombre)::text)), '/\"'';,_-.âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ'::text, '         aaaaaaaaaAAAAAAAAAeeeeeeeeeeEEEEEEEEEiiiiiiiiIIIIIIIIooooooooOOOOOOOOuuuuuuuuUUUUUUUUcCnN'::text));


--
-- TOC entry 2098 (class 2620 OID 139091)
-- Name: banco tgformatbanco; Type: TRIGGER; Schema: massoftware; Owner: postgres
--

CREATE TRIGGER tgformatbanco BEFORE INSERT OR UPDATE ON banco FOR EACH ROW EXECUTE PROCEDURE ftgformatbanco();


--
-- TOC entry 2102 (class 2620 OID 139175)
-- Name: cuentafondo tgformatcuentafondo; Type: TRIGGER; Schema: massoftware; Owner: postgres
--

CREATE TRIGGER tgformatcuentafondo BEFORE INSERT OR UPDATE ON cuentafondo FOR EACH ROW EXECUTE PROCEDURE ftgformatcuentafondo();


--
-- TOC entry 2100 (class 2620 OID 139127)
-- Name: cuentafondogrupo tgformatcuentafondogrupo; Type: TRIGGER; Schema: massoftware; Owner: postgres
--

CREATE TRIGGER tgformatcuentafondogrupo BEFORE INSERT OR UPDATE ON cuentafondogrupo FOR EACH ROW EXECUTE PROCEDURE ftgformatcuentafondogrupo();


--
-- TOC entry 2099 (class 2620 OID 139107)
-- Name: cuentafondorubro tgformatcuentafondorubro; Type: TRIGGER; Schema: massoftware; Owner: postgres
--

CREATE TRIGGER tgformatcuentafondorubro BEFORE INSERT OR UPDATE ON cuentafondorubro FOR EACH ROW EXECUTE PROCEDURE ftgformatcuentafondorubro();


--
-- TOC entry 2101 (class 2620 OID 139143)
-- Name: cuentafondotipo tgformatcuentafondotipo; Type: TRIGGER; Schema: massoftware; Owner: postgres
--

CREATE TRIGGER tgformatcuentafondotipo BEFORE INSERT OR UPDATE ON cuentafondotipo FOR EACH ROW EXECUTE PROCEDURE ftgformatcuentafondotipo();


--
-- TOC entry 2097 (class 2606 OID 139168)
-- Name: cuentafondo cuentafondo_banco_fkey; Type: FK CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondo
    ADD CONSTRAINT cuentafondo_banco_fkey FOREIGN KEY (banco) REFERENCES banco(id);


--
-- TOC entry 2095 (class 2606 OID 139158)
-- Name: cuentafondo cuentafondo_cuentafondogrupo_fkey; Type: FK CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondo
    ADD CONSTRAINT cuentafondo_cuentafondogrupo_fkey FOREIGN KEY (cuentafondogrupo) REFERENCES cuentafondogrupo(id);


--
-- TOC entry 2096 (class 2606 OID 139163)
-- Name: cuentafondo cuentafondo_cuentafondotipo_fkey; Type: FK CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondo
    ADD CONSTRAINT cuentafondo_cuentafondotipo_fkey FOREIGN KEY (cuentafondotipo) REFERENCES cuentafondotipo(id);


--
-- TOC entry 2094 (class 2606 OID 139119)
-- Name: cuentafondogrupo cuentafondogrupo_cuentafondorubro_fkey; Type: FK CONSTRAINT; Schema: massoftware; Owner: postgres
--

ALTER TABLE ONLY cuentafondogrupo
    ADD CONSTRAINT cuentafondogrupo_cuentafondorubro_fkey FOREIGN KEY (cuentafondorubro) REFERENCES cuentafondorubro(id);


-- Completed on 2019-01-10 14:49:24

--
-- PostgreSQL database dump complete
--

