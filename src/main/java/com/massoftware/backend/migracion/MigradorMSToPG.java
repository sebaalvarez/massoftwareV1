package com.massoftware.backend.migracion;

import com.massoftware.backend.BackendContextMS;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.Banco;
import com.massoftware.model.Caja;
import com.massoftware.model.CentroCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaFondo;
import com.massoftware.model.CuentaFondoGrupo;
import com.massoftware.model.CuentaFondoRubro;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Firmante;
import com.massoftware.model.PuntoEquilibrio;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.Sucursal;
import com.massoftware.model.Talonario;

public class MigradorMSToPG {

	public static void main(String[] args) {
		try {

			migrar();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void migrar() throws Exception {

		System.out.println("\n\nStart Migrador\n\n");

		modulo();

		puerta();

		ejercicioContable();

		puntoEquilibrio();

		centroCostoContable();

		cuentaContable();
		
		asientoModelo();
		
		asientoModeloItem();

		caja();

		banco();

		rubro();

		grupo();

		cuentaFondo();

		sucursal();

		talonario();

		firmante();

		System.out.println("\n\nEnd Migrador\n\n");

	}

	public static void ejercicioContable() throws Exception {

		// SELECT A.EJERCICIO, A.FECHAAPERTURASQL, A.FECHACIERRESQL, A.EJERCICIOCERRADO,
		// A.EJERCICIOCERRADOMODULOS FROM EjerciciosContables A ORDER BY A.EJERCICIO
		// DESC

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(A.EJERCICIO AS VARCHAR)";
		String attNumero = "CAST(A.EJERCICIO AS INTEGER)";
		String apertura = "A.FECHAAPERTURASQL";
		String cierre = "A.FECHACIERRESQL";
		String cerrado = "CAST(A.EJERCICIOCERRADO AS BIT)";
		String cerradoModulos = "CAST(A.EJERCICIOCERRADOMODULOS AS BIT)";
		String comentario = "A.COMENTARIO";

		String tableSQL = "EjerciciosContables A";

		String attsSQL = attId + ", " + attNumero + ", " + apertura + ", " + cierre + ", " + cerrado + ", "
				+ cerradoModulos + ", " + comentario;
		String orderBySQL = attNumero + " DESC";
		String whereSQL = null;

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			EjercicioContable item = new EjercicioContable();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void banco() throws Exception {

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(A.BANCO AS VARCHAR)";
		String attNumero = "CAST(A.BANCO AS INTEGER)";
		String attNombre = "A.NOMBRE";
		String attCuit = "CASE WHEN A.CUIT = 0 THEN null ELSE CAST(A.CUIT AS BIGINT) END";
		String attBloqueado = "CAST(A.BLOQUEADO AS BIT)";
		String attHoja = "CAST(A.HOJA AS INTEGER)";
		String attPrimeraFila = "A.PRIMERAFILA";
		String attUltimaFila = "A.ULTIMAFILA";
		String attFecha = "A.COLUMNAFECHA";
		String attDescripcion = "A.COLUMNADESCRIPCION";
		String attReferencia1 = "A.COLUMNAREFERENCIA1";
		String attReferencia2 = "A.COLUMNAREFERENCIA2";
		String attImporte = "A.COLUMNAIMPORTE";
		String attSaldo = "A.COLUMNASALDO";

		String tableSQL = "Bancos A";

		String attsSQL = attId + ", " + attNumero + ", " + attNombre + ", " + attCuit + ", " + attBloqueado + ", "
				+ attHoja + ", " + attPrimeraFila + ", " + attUltimaFila + ", " + attFecha + ", " + attDescripcion
				+ ", " + attReferencia1 + ", " + attReferencia2 + ", " + attImporte + ", " + attSaldo;
		String orderBySQL = attNumero;
		String whereSQL = null;

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			Banco item = new Banco();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void rubro() throws Exception {

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(A.RUBRO AS VARCHAR)";
		String attNumero = "CAST(A.RUBRO AS INTEGER)";
		String attNombre = "A.NOMBRE";

		String tableSQL = "CuentasDeFondosRubro A";

		String attsSQL = attId + ", " + attNumero + ", " + attNombre;
		String orderBySQL = attNumero;
		String whereSQL = null;

		// ==================================================================

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			CuentaFondoRubro item = new CuentaFondoRubro();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void grupo() throws Exception {

		// ==================================================================
		// MS SQL SERVER

		String attId = " CONCAT (RUBRO, '-', GRUPO )";
		String attNumeroRubro = "CASE WHEN RUBRO = 0 THEN null ELSE RUBRO END";
		String attNumero = "CAST(GRUPO AS INTEGER)";
		String attNombre = "NOMBRE";

		String tableSQL = "CuentasDeFondosGrupo A";

		String attsSQL = attId + ", " + attNumeroRubro + ", " + attNumero + ", " + attNombre;
		String orderBySQL = attNumeroRubro + ", " + attNumero;
		String whereSQL = null;

		// ==================================================================

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			CuentaFondoGrupo item = new CuentaFondoGrupo();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void cuentaFondo() throws Exception {

		// ==================================================================
		// MS SQL SERVER

		String id = "CUENTA";
		String cuentaFondoGrupo = "CONCAT(CASE WHEN RUBRO = 0 THEN null ELSE RUBRO END, '-', CASE WHEN GRUPO = 0 THEN null ELSE GRUPO END)";
		String numero = "CAST(CUENTA AS INTEGER)";
		String nombre = "NOMBRE";
		String cuentaFondoTipo = "TIPO";
		String banco = " CASE WHEN BANCO = 0 THEN null ELSE BANCO END";
		String bloqueado = "CAST(OBSOLETA AS BIT)";

		String tableSQL = "CuentasDeFondos";

		String attsSQL = id + ", " + cuentaFondoGrupo + ", " + numero + ", " + nombre + ", " + cuentaFondoTipo + ", "
				+ banco + ", " + bloqueado;
		String orderBySQL = numero;
		String whereSQL = null;

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			CuentaFondo item = new CuentaFondo();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void modulo() throws Exception {

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(A.NO AS VARCHAR)";
		String attNumero = "A.NO";
		String attNombre = "A.NAME";

		String tableSQL = "SSECUR_DoorGroup A";

		String attsSQL = attId + ", " + attNumero + ", " + attNombre;
		String orderBySQL = attNumero;
		String whereSQL = null;

		// ==================================================================

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			SeguridadModulo item = new SeguridadModulo();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void puerta() throws Exception {

		// SELECT A."NO", A.DGRPNO, A.EQUATE, A.DESCRIPTION FROM dbo.SSECUR_Door A WHERE
		// ( A.DGRPNO = 3 ) ORDER BY A.DGRPNO, UPPER( A.DESCRIPTION)

		// ==================================================================
		// MS SQL SERVER

		// String attId = " CONCAT (DGRPNO, '-', NO )";
		String attId = "CAST(NO AS VARCHAR)";
		String attNumeroModulo = "CASE WHEN DGRPNO = 0 THEN null ELSE DGRPNO END";
		String attNumero = "CAST(NO AS INTEGER)";
		String attNombre = "DESCRIPTION";
		String attEquate = "EQUATE";

		String tableSQL = "SSECUR_Door";

		String attsSQL = attId + ", " + attNumeroModulo + ", " + attNumero + ", " + attNombre + ", " + attEquate;
		String orderBySQL = attNumeroModulo + ", " + attNumero;
		String whereSQL = null;

		// ==================================================================

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			SeguridadPuerta item = new SeguridadPuerta();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void caja() throws Exception {

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(A.CAJA AS VARCHAR)";
		String attNumero = "CAST(A.CAJA AS INTEGER)";
		String attNombre = "A.NOMBRE";
		String attPuerta = "A.DOORNOPERMISO";

		String tableSQL = "Cajas A";

		String attsSQL = attId + ", " + attNumero + ", " + attNombre + ", " + attPuerta;
		String orderBySQL = attNumero;
		String whereSQL = null;

		// ==================================================================

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			Caja item = new Caja();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void sucursal() throws Exception {

		// 'SELECT A.SUCURSAL, A.NOMBRE, A.TIPOSUCURSAL FROM Sucursales A ORDER BY
		// A.NOMBRE'

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(A.SUCURSAL AS VARCHAR)";
		String attTipo = "A.TIPOSUCURSAL";
		String attNumero = "CAST(A.SUCURSAL AS INTEGER)";
		String attNombre = "A.NOMBRE";

		String attAbreviatura = "A.ABREVIATURA";

		String cuentaClienteDesde = "A.CUENTASCLIENTESDESDE";
		String cuentaClienteHasa = "A.CUENTASCLIENTESHASTA";
		String cantidadCaracteresCliente = "CAST(A.CANTIDADCARACTERESCLIENTES AS INTEGER)";
		String identificacionNumericaCliente = "CAST(A.NUMERICOCLIENTES AS BIT)";
		String permiteCambiarCliente = "CAST(A.PERMITECAMBIARCLIENTES AS BIT)";
		// -- --------------------------------------------------------
		String clientesOcacionalesDesde = "CASE WHEN A.CUENTASCLIENTESOCASIONALESDESDE = 0 THEN null ELSE CAST(A.CUENTASCLIENTESOCASIONALESDESDE AS INTEGER) END";
		String clientesOcacionalesHasa = "CASE WHEN A.CUENTASCLIENTESOCASIONALESHASTA = 0 THEN null ELSE CAST(A.CUENTASCLIENTESOCASIONALESHASTA AS INTEGER) END";
		// -- --------------------------------------------------------
		String nroCobranzaDesde = "CASE WHEN A.NROCOBRANZADESDE = 0 THEN null ELSE CAST(A.NROCOBRANZADESDE AS INTEGER) END";
		String nroCobranzaHasa = "CASE WHEN A.NROCOBRANZAHASTA = 0 THEN null ELSE CAST(A.NROCOBRANZAHASTA AS INTEGER) END";
		// -- --------------------------------------------------------
		String proveedoresDesde = "A.CUENTASPROVEEDORESDESDE";
		String proveedoresHasa = "A.CUENTASPROVEEDORESHASTA";
		String cantidadCaracteresProveedor = "CAST(A.CANTIDADCARACTERESPROVEEDOR AS INTEGER)";
		String identificacionNumericaProveedor = "CAST(A.NUMERICOPROVEEDOR AS BIT)";
		String permiteCambiarProveedor = "CAST(A.PERMITECAMBIARPROVEEDOR AS BIT)";

		String tableSQL = "Sucursales A";

		String attsSQL = attId + ", " + attTipo + ", " + attNumero + ", " + attNombre + ", " + attAbreviatura + ", "
				+ cuentaClienteDesde + ", " + cuentaClienteHasa + ", " + cantidadCaracteresCliente + ", "
				+ identificacionNumericaCliente + ", " + permiteCambiarCliente + ", " + clientesOcacionalesDesde + ", "
				+ clientesOcacionalesHasa + ", " + nroCobranzaDesde + ", " + nroCobranzaHasa + ", " + proveedoresDesde
				+ ", " + proveedoresHasa + ", " + cantidadCaracteresProveedor + ", " + identificacionNumericaProveedor
				+ ", " + permiteCambiarProveedor;

		String orderBySQL = attNumero;
		String whereSQL = null;

		// ==================================================================

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			Sucursal item = new Sucursal();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void talonario() throws Exception {

		// SELECT A.MULTIPROPOSITO, A.NOMBRE, A.LETRA, A.SUCURSAL, A.PROXIMONUMERO FROM
		// TablaDeMultiproposito A ORDER BY A.MULTIPROPOSITO

		// ==================================================================
		// MS SQL SERVER

		String id = "CAST(A.MULTIPROPOSITO AS VARCHAR)";
		String numero = "CAST(A.MULTIPROPOSITO AS INTEGER)";
		String nombre = "A.NOMBRE";
		String talonarioLetra = "A.LETRA";
		String puntoVenta = "CAST(A.SUCURSAL AS INTEGER)";
		String autonumeracion = "CAST(A.AUTONUMERACION AS BIT)";
		String numeracionPreImpresa = "CAST(A.NUMERACIONPREIMPRESA AS BIT)";
		String asociadoRG10098 = "CAST(A.RG10098 AS BIT)";
		String talonarioControladorFizcal = "A.CONTROLFISCAL";
		String primerNumero = "CASE WHEN A.PRIMERNUMERO = 0 THEN null ELSE CAST(A.PRIMERNUMERO AS INTEGER) END";
		String proximoNumero = "CASE WHEN A.PROXIMONUMERO = 0 THEN null ELSE CAST(A.PROXIMONUMERO AS INTEGER) END";
		String ultimoNumero = "CASE WHEN A.ULTIMONUMERO = 0 THEN null ELSE CAST(A.ULTIMONUMERO AS INTEGER) END";
		String cantidadMinimaComprobantes = "CASE WHEN A.ALERTACANTIDADMINIMADECBTES = 0 THEN null ELSE CAST(A.ALERTACANTIDADMINIMADECBTES AS INTEGER) END";
		String fecha = "A.ULTIMAFECHASQL";
		String numeroCAI = "CASE WHEN A.CAI = 0 THEN null ELSE A.CAI END";
		String vencimiento = "A.VENCIMIENTOCAISQL";
		String diasAvisoVencimiento = "CASE WHEN A.DIASAVISOVENCIMIENTO = 0 THEN null ELSE CAST(A.DIASAVISOVENCIMIENTO AS INTEGER) END";

		String tableSQL = "TablaDeMultiproposito A";

		String attsSQL = id + ", " + numero + ", " + nombre + ", " + talonarioLetra + ", " + puntoVenta + ", "
				+ autonumeracion + ", " + numeracionPreImpresa + ", " + asociadoRG10098 + ", "
				+ talonarioControladorFizcal + ", " + primerNumero + ", " + proximoNumero + ", " + ultimoNumero + ", "
				+ cantidadMinimaComprobantes + ", " + fecha + ", " + numeroCAI + ", " + vencimiento + ", "
				+ diasAvisoVencimiento;

		String orderBySQL = numero;
		String whereSQL = null;

		// ==================================================================

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			Talonario item = new Talonario();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void firmante() throws Exception {

		// SELECT A.CODIGO, A.NOMBRE, A.CARGO, A.ACTIVO FROM BancosFirmantes A ORDER BY
		// A.CODIGO

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(A.CODIGO AS VARCHAR)";
		String attNumero = "CAST(A.CODIGO AS INTEGER)";
		String attNombre = "A.NOMBRE";
		String attCargo = "A.CARGO";
		String attBloqueado = "CASE WHEN A.ACTIVO = 1 THEN CAST(0 AS BIT) ELSE CAST(A.ACTIVO AS BIT) END";

		String tableSQL = "BancosFirmantes A";

		String attsSQL = attId + ", " + attNumero + ", " + attNombre + ", " + attCargo + ", " + attBloqueado;
		String orderBySQL = attNumero;
		String whereSQL = null;

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			Firmante item = new Firmante();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void puntoEquilibrio() throws Exception {

		// SELECT A.EJERCICIO, A.PUNTODEEQUILIBRIO, A.NOMBRE FROM PuntoDeEquilibrio A
		// ORDER BY A.EJERCICIO, A.PUNTODEEQUILIBRIO

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(CONCAT(CAST(A.EJERCICIO AS VARCHAR), '-', CAST(A.PUNTODEEQUILIBRIO AS VARCHAR)) AS VARCHAR)";
		String ejercicioContable = "CAST(A.EJERCICIO AS VARCHAR)";
		String puntoEquilibrioTipo = "CAST(A.TIPO AS VARCHAR)";
		String attNumero = "CAST(A.PUNTODEEQUILIBRIO AS INTEGER)";
		String attNombre = "CAST(A.NOMBRE AS VARCHAR(40))";

		String tableSQL = "PuntoDeEquilibrio A";

		String attsSQL = attId + ", " + ejercicioContable + ", " + puntoEquilibrioTipo + ", " + attNumero + ", "
				+ attNombre;
		String orderBySQL = ejercicioContable + ", " + attNumero;
		String whereSQL = null;

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			PuntoEquilibrio item = new PuntoEquilibrio();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void centroCostoContable() throws Exception {

		// SELECT A.EJERCICIO, A.PUNTODEEQUILIBRIO, A.NOMBRE FROM PuntoDeEquilibrio A
		// ORDER BY A.EJERCICIO, A.PUNTODEEQUILIBRIO

		// ==================================================================
		// MS SQL SERVER

		String attId = "CAST(CONCAT(CAST(A.EJERCICIO AS VARCHAR), '-', CAST(A.CENTRODECOSTOCONTABLE AS VARCHAR)) AS VARCHAR)";
		String ejercicioContable = "CAST(A.EJERCICIO AS VARCHAR)";
		String attNumero = "CAST(A.CENTRODECOSTOCONTABLE AS INTEGER)";
		String attNombre = "CAST(A.NOMBRE AS VARCHAR(30))";
		String attAbreviatura = "CAST(A.ABREVIATURA AS VARCHAR(12))";

		String tableSQL = "CentrosDeCostoContable A";

		String attsSQL = attId + ", " + ejercicioContable + ", " + attNumero + ", " + attNombre + ", " + attAbreviatura;
		String orderBySQL = ejercicioContable + ", " + attNumero;
		String whereSQL = null;

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			CentroCostoContable item = new CentroCostoContable();
			item.setter(table[i], 0);
			item.insert();

		}

	}

	public static void cuentaContable() throws Exception {

		// SELECT
		// CUENTACONTABLE
		// ,CUENTAINTEGRADORA
		// ,CUENTADEJERARQUIAIND
		// ,NOMBRE
		// ,IMPUTABLE
		// ,APROPIA
		// ,AJUSTEINF
		// ,DOORNO
		// ,ESTADO
		// ,CENTRODECOSTOCONTABLE
		// ,PUNTODEEQUILIBRIO
		// ,COSTODEVENTA
		// ,CUENTAAGRUPADORA
		// ,PORCENTAJE
		// ,EJERCICIO
		// FROM PlanDeCuentas

		// ==================================================================
		// MS SQL SERVER

		String id = "CAST(CONCAT(CAST(A.EJERCICIO AS VARCHAR), '-', CAST(A.CUENTACONTABLE AS VARCHAR(11))) AS VARCHAR)";
		String ejercicioContable = "CAST(A.EJERCICIO AS VARCHAR)";
		String integra = "CAST(A.CUENTAINTEGRADORA AS VARCHAR(11))";
		String cuentaJerarquia = "CAST(A.CUENTADEJERARQUIAIND AS VARCHAR(11))";
		String codigo = "CAST(A.CUENTACONTABLE AS VARCHAR(11))";
		String nombre = "CAST(A.NOMBRE AS VARCHAR(35))";
		// --------------------------------------------------------------------------

		String imputable = "CASE WHEN A.IMPUTABLE = 'N' THEN CAST(0 AS BIT) ELSE CAST(1 AS BIT) END";
		String ajustaPorInflacion = "CASE WHEN A.AJUSTEINF = 'N' THEN CAST(0 AS BIT) ELSE CAST(1 AS BIT) END";
		String cuentaContableEstado = "CAST(A.ESTADO AS VARCHAR)";
		String cuentaConApropiacion = "CAST(A.APROPIA AS BIT)";
		// --------------------------------------------------------------------------
		String centroCostoContable = "CASE WHEN A.CENTRODECOSTOCONTABLE IS NOT NULL THEN CAST(CONCAT(CAST(A.EJERCICIO AS VARCHAR), '-', CAST(A.CENTRODECOSTOCONTABLE AS VARCHAR)) AS VARCHAR) ELSE null END";
		String cuentaAgrupadora = "CAST(A.CUENTAAGRUPADORA AS VARCHAR(11))";
		String porcentaje = "CASE WHEN A.PORCENTAJE = 0 THEN null ELSE CAST(A.PORCENTAJE AS DOUBLE PRECISION) END";
		String puntoEquilibrio = "CASE WHEN A.PUNTODEEQUILIBRIO IS NOT NULL THEN CAST(CONCAT(CAST(A.EJERCICIO AS VARCHAR), '-', CAST(A.CENTRODECOSTOCONTABLE AS VARCHAR)) AS VARCHAR) ELSE null END";
		// String puntoEquilibrio = "CAST(CONCAT(CAST(A.EJERCICIO AS VARCHAR), '-',
		// CAST(A.PUNTODEEQUILIBRIO AS VARCHAR)) AS VARCHAR)";
		String costoVenta = "CAST(A.COSTODEVENTA AS VARCHAR)";
		String seguridadPuerta = "CAST(A.DOORNO AS VARCHAR)";

		String tableSQL = "PlanDeCuentas A";

		String attsSQL = id + ", " + ejercicioContable + ", " + integra + ", " + cuentaJerarquia + ", " + codigo + ", "
				+ nombre + ", " + imputable + ", " + ajustaPorInflacion + ", " + cuentaContableEstado + ", "
				+ cuentaConApropiacion + ", " + centroCostoContable + ", " + cuentaAgrupadora + ", " + porcentaje + ", "
				+ puntoEquilibrio + ", " + costoVenta + ", " + seguridadPuerta;
		String orderBySQL = ejercicioContable + ", " + codigo;
		String whereSQL = null;

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(tableSQL, attsSQL, orderBySQL, whereSQL, -1, -1, null);

		for (int i = 0; i < table.length; i++) {

			CuentaContable item = new CuentaContable();
			item.setter(table[i], 0);
			item.setterTrim();
			if (item.getIntegra() != null) {
				item.insert();
			}

		}

	}

	public static void asientoModelo() throws Exception {


		// ==================================================================
		// MS SQL SERVER

		String sql = "SELECT	DISTINCT CONCAT(CAST(B.EJERCICIO AS VARCHAR(250)), '-', CAST(B.ASIENTOMODELO AS VARCHAR(250))) AS id, CAST(B.EJERCICIO AS VARCHAR(250)) AS ejercicioContable, CAST(B.ASIENTOMODELO AS INTEGER) AS numero, (SELECT A.DENOMINACION FROM AsientosModelos A WHERE A.ASIENTOMODELO = B.ASIENTOMODELO) AS nombre	FROM AsientosModelosMov B ORDER BY CAST(B.EJERCICIO AS VARCHAR(250)), CAST(B.ASIENTOMODELO AS INTEGER)";

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(sql);

		for (int i = 0; i < table.length; i++) {

			AsientoModelo item = new AsientoModelo();
			item.setter(table[i], 0);
			item.setterTrim();
			item.insert();

		}

	}
	
	public static void asientoModeloItem() throws Exception {


		// ==================================================================
		// MS SQL SERVER
		
		String sql = "SELECT	CONCAT(CAST(B.EJERCICIO AS VARCHAR), '-', CAST(B.ASIENTOMODELO AS VARCHAR(250)), '-', CAST(B.REGISTRO AS VARCHAR(250))) AS id, CONCAT(CAST(B.EJERCICIO AS VARCHAR(250)), '-', CAST(B.ASIENTOMODELO AS VARCHAR(250))) AS asientoModelo, CAST(B.REGISTRO AS INTEGER) AS numero, CAST(CONCAT(CAST(B.EJERCICIO AS VARCHAR), '-', CAST(B.CUENTACONTABLE AS VARCHAR(11))) AS VARCHAR) AS cuentaContable FROM	AsientosModelosMov B ORDER BY CAST(B.ASIENTOMODELO AS INTEGER), CAST(B.REGISTRO AS INTEGER)";

		// ==================================================================

		Object[][] table = BackendContextMS.get().find(sql);

		for (int i = 0; i < table.length; i++) {

			AsientoModeloItem item = new AsientoModeloItem();
			item.setter(table[i], 0);
			item.setterTrim();
			item.insert();

		}

	}

}
