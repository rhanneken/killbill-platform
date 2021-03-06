/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2014 Groupon, Inc
 * Copyright 2014 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.osgi.bundles.test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.osgi.bundles.test.dao.TestDao;
import org.killbill.billing.payment.api.PaymentMethodPlugin;
import org.killbill.billing.payment.api.PluginProperty;
import org.killbill.billing.payment.api.TransactionType;
import org.killbill.billing.payment.plugin.api.GatewayNotification;
import org.killbill.billing.payment.plugin.api.HostedPaymentPageFormDescriptor;
import org.killbill.billing.payment.plugin.api.PaymentMethodInfoPlugin;
import org.killbill.billing.payment.plugin.api.PaymentPluginApi;
import org.killbill.billing.payment.plugin.api.PaymentPluginApiException;
import org.killbill.billing.payment.plugin.api.PaymentPluginStatus;
import org.killbill.billing.payment.plugin.api.PaymentTransactionInfoPlugin;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.entity.Pagination;

public class TestPaymentPluginApi implements PaymentPluginApi {

    private final TestDao testDao;

    public TestPaymentPluginApi(final TestDao testDao) {
        this.testDao = testDao;
    }

    @Override
    public PaymentTransactionInfoPlugin authorizePayment(final UUID kbAccountId, final UUID kbPaymentId, final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal bigDecimal, final Currency currency, final Iterable<PluginProperty> properties, final CallContext callContext) throws PaymentPluginApiException {
        return null;
    }

    @Override
    public PaymentTransactionInfoPlugin capturePayment(final UUID kbAccountId, final UUID kbPaymentId, final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal bigDecimal, final Currency currency, final Iterable<PluginProperty> properties, final CallContext callContext) throws PaymentPluginApiException {
        return null;
    }

    @Override
    public PaymentTransactionInfoPlugin purchasePayment(final UUID kbAccountId, final UUID kbPaymentId, final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal amount, final Currency currency, final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        testDao.insertProcessedPayment(kbPaymentId, kbPaymentMethodId, amount);
        return new PaymentTransactionInfoPlugin() {
            @Override
            public UUID getKbPaymentId() {
                return kbPaymentId;
            }

            @Override
            public UUID getKbTransactionPaymentId() {
                return kbTransactionId;
            }

            @Override
            public TransactionType getTransactionType() {
                return TransactionType.PURCHASE;
            }

            @Override
            public BigDecimal getAmount() {
                return amount;
            }

            @Override
            public Currency getCurrency() {
                return currency;
            }

            @Override
            public DateTime getCreatedDate() {
                return new DateTime();
            }

            @Override
            public DateTime getEffectiveDate() {
                return new DateTime();
            }

            @Override
            public PaymentPluginStatus getStatus() {
                return PaymentPluginStatus.PROCESSED;
            }

            @Override
            public String getGatewayError() {
                return null;
            }

            @Override
            public String getGatewayErrorCode() {
                return null;
            }

            @Override
            public String getFirstPaymentReferenceId() {
                return null;
            }

            @Override
            public String getSecondPaymentReferenceId() {
                return null;
            }

            @Override
            public List<PluginProperty> getProperties() {
                return new LinkedList<PluginProperty>();
            }
        };
    }

    @Override
    public PaymentTransactionInfoPlugin voidPayment(final UUID kbAccountId, final UUID kbPaymentId, final UUID kbTransactionId, final UUID kbPaymentMethodId, final Iterable<PluginProperty> properties, final CallContext callContext) throws PaymentPluginApiException {
        return null;
    }

    @Override
    public PaymentTransactionInfoPlugin creditPayment(final UUID kbAccountId, final UUID kbPaymentId, final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal amount, final Currency currency, final Iterable<PluginProperty> properties, final CallContext context)
            throws PaymentPluginApiException {
        return null;
    }

    @Override
    public List<PaymentTransactionInfoPlugin> getPaymentInfo(final UUID kbAccountId, final UUID kbPaymentId, final Iterable<PluginProperty> properties, final TenantContext context) throws PaymentPluginApiException {
        return new LinkedList<PaymentTransactionInfoPlugin>();
    }

    @Override
    public Pagination<PaymentTransactionInfoPlugin> searchPayments(final String searchKey, final Long offset, final Long limit, final Iterable<PluginProperty> properties, final TenantContext tenantContext) throws PaymentPluginApiException {
        return new Pagination<PaymentTransactionInfoPlugin>() {
            @Override
            public Long getCurrentOffset() {
                return 0L;
            }

            @Override
            public Long getNextOffset() {
                return null;
            }

            @Override
            public Long getMaxNbRecords() {
                return 0L;
            }

            @Override
            public Long getTotalNbRecords() {
                return 0L;
            }

            @Override
            public Iterator<PaymentTransactionInfoPlugin> iterator() {
                return null;
            }
        };
    }

    @Override
    public PaymentTransactionInfoPlugin refundPayment(final UUID kbAccountId, final UUID kbPaymentId, final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal refundAmount, final Currency currency, final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        return null;
    }

    @Override
    public void addPaymentMethod(final UUID kbAccountId, final UUID kbPaymentMethodId, final PaymentMethodPlugin paymentMethodProps, final boolean setDefault, final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
    }

    @Override
    public void deletePaymentMethod(final UUID kbAccountId, final UUID kbPaymentMethodId, final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
    }

    @Override
    public PaymentMethodPlugin getPaymentMethodDetail(final UUID kbAccountId, final UUID kbPaymentMethodId, final Iterable<PluginProperty> properties, final TenantContext context) throws PaymentPluginApiException {
        return null;
    }

    @Override
    public void setDefaultPaymentMethod(final UUID kbAccountId, final UUID kbPaymentMethodId, final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
    }

    @Override
    public List<PaymentMethodInfoPlugin> getPaymentMethods(final UUID kbAccountId, final boolean refreshFromGateway, final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        return Collections.emptyList();
    }

    @Override
    public Pagination<PaymentMethodPlugin> searchPaymentMethods(final String searchKey, final Long offset, final Long limit, final Iterable<PluginProperty> properties, final TenantContext tenantContext) throws PaymentPluginApiException {
        return new Pagination<PaymentMethodPlugin>() {
            @Override
            public Long getCurrentOffset() {
                return 0L;
            }

            @Override
            public Long getNextOffset() {
                return null;
            }

            @Override
            public Long getMaxNbRecords() {
                return 0L;
            }

            @Override
            public Long getTotalNbRecords() {
                return 0L;
            }

            @Override
            public Iterator<PaymentMethodPlugin> iterator() {
                return null;
            }
        };
    }

    @Override
    public void resetPaymentMethods(final UUID kbAccountId, final List<PaymentMethodInfoPlugin> paymentMethods, final Iterable<PluginProperty> properties, final CallContext callContext) throws PaymentPluginApiException {
    }

    @Override
    public HostedPaymentPageFormDescriptor buildFormDescriptor(final UUID kbAccountId, final Iterable<PluginProperty> customFields, final Iterable<PluginProperty> properties, final CallContext callContext) {
        return null;
    }

    @Override
    public GatewayNotification processNotification(final String notification, final Iterable<PluginProperty> properties, final CallContext callContext) throws PaymentPluginApiException {
        return null;
    }
}
